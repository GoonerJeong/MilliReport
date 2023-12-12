package com.report.news.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.report.news.R
import com.report.news.domain.model.Headline
import com.report.news.ui.theme.NewsTheme
import com.report.news.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NewsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ShowList(viewModel)
                    showLoading(viewModel)
                    showErrorToast(viewModel)
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

@Composable
fun ShowList(viewModel: MainViewModel) {

    val datas by viewModel.headlineList.collectAsState(initial = emptyList())

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp

    if(screenWidthDp > 600.dp){
        var cellCount = 3

        LazyVerticalGrid(
            columns = GridCells.Fixed(cellCount)
        ){
            items(datas.size) {
                getNewsItem(viewModel, datas.get(it), screenWidthDp / cellCount)
            }
        }
    }
    else{
        LazyColumn{
            items(datas.size) {
                getNewsItem(viewModel, datas.get(it), screenWidthDp)
            }
        }
    }
}


@Composable
fun getNewsItem(viewModel: MainViewModel, headlineModel: Headline, height : Dp) {

    var isRetrieveNews by remember { mutableStateOf(false) }
    isRetrieveNews = headlineModel.isRetrieveNews

    val activity = LocalContext.current as Activity

    Box(
        Modifier
            .fillMaxWidth()
            .height(height)
            .padding(5.dp)
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RectangleShape
            )
            .clickable {
                headlineModel.url?.let {
                    Intent(activity, WebActivity::class.java).apply {
                        this.putExtra("URL", it)
                        activity.startActivity(this)
                    }

                    isRetrieveNews = true
                    headlineModel.isRetrieveNews = true
                    viewModel.updateRetrieveNews(headlineModel)
                }

            }) {

        val listener = object : ImageRequest.Listener {
            override fun onError(request: ImageRequest, result: ErrorResult) {
                super.onError(request, result)
            }

            override fun onSuccess(request: ImageRequest, result: SuccessResult) {
                super.onSuccess(request, result)
            }
        }

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(headlineModel.urlToImage)
            .listener(listener)
            .dispatcher(Dispatchers.IO)
            .memoryCacheKey(headlineModel.urlToImage)
            .diskCacheKey(headlineModel.urlToImage)
            .placeholder(R.drawable.image_ic)
            .error(R.drawable.image_ic)
            .fallback(R.drawable.image_ic)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build()


        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        )

        Box(
            Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0x7F000000))
                .align(Alignment.BottomStart)
           ) {
            headlineModel.title?.let {
                Text(text = it,
                    Modifier
                        .fillMaxWidth()
                        .offset()
                        .padding(5.dp)
                        .alpha(1.0f),
                    color =  if (!isRetrieveNews) {
                                Color.White
                            } else {
                                Color.Red
                            },
                    fontSize = 16.sp,
                    maxLines = 2)
            }

            headlineModel.publishedAt?.let {
                val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                val formattedDate = LocalDateTime.parse(it, dateFormatter)
                val res = DateTimeFormatter.ofPattern("YYYY년 MMMM d일 a hh:mm").format(formattedDate)
                Text(text = res,
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomStart)
                        .alpha(1.0f)
                        .padding(5.dp),
                    color = Color.White,
                    fontSize = 14.sp)
            }
        }
    }

}

@Composable
fun showLoading(viewModel: MainViewModel){

    val showLoading by viewModel.showLoading.collectAsState(false)

    if(showLoading){
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun showErrorToast(viewModel: MainViewModel){
    val showErrorToast by viewModel.showErrorToast.collectAsState(null)

    showErrorToast?.let {
        Toast.makeText(LocalContext.current, it, Toast.LENGTH_SHORT).show()
    }
}

