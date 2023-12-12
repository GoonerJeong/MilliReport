package com.report.news.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.report.news.data.source.local.entity.NewsEntity
import com.report.news.domain.model.Headline
import com.report.news.domain.translator.NewsTranslator.toHeadlineModelList
import com.report.news.domain.translator.NewsTranslator.toNewsEntity
import com.report.news.domain.usecase.GetHeadlineNewsListUseCase
import com.report.news.domain.usecase.UpdateRetrieveNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject internal constructor(
    private val getHeadlineNewsListUseCase: GetHeadlineNewsListUseCase,
    private val updateRetrieveNewsUseCase : UpdateRetrieveNewsUseCase
): ViewModel() {
    private val _headlineList = MutableStateFlow<List<Headline>>(listOf())
    val headlineList: StateFlow<List<Headline>> = _headlineList

    private val _showLoading = MutableStateFlow(false)
    val showLoading: StateFlow<Boolean> = _showLoading

    private val _showErrorToast = MutableStateFlow<String?>(null)
    val showErrorToast: StateFlow<String?> = _showErrorToast

    init {
        getHeadlineList()
    }

    private fun getHeadlineList() {

        viewModelScope.launch(Dispatchers.IO) {
            getHeadlineNewsListUseCase().collectLatest {

                when (it){
                    is HeadlineNewsUiState.Loading->{
                        _showLoading.value = it.isShow
                    }
                    is HeadlineNewsUiState.Success->{
                        _showLoading.value = false
                        _headlineList.emit(it.news.toHeadlineModelList())
                    }
                    is HeadlineNewsUiState.Error->{
                        _showLoading.value = false
                        _showErrorToast.value = it.exception.message
                    }
                }
            }
        }
    }


    fun updateRetrieveNews(data : Headline){
        viewModelScope.launch(Dispatchers.IO) {
            updateRetrieveNewsUseCase(data.toNewsEntity())
        }
    }
}

sealed class HeadlineNewsUiState {
    data class Loading(val isShow:Boolean): HeadlineNewsUiState()
    data class Success(val news: List<NewsEntity>): HeadlineNewsUiState()
    data class Error(val exception: Throwable): HeadlineNewsUiState()
}