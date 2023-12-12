package com.report.news.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.report.news.domain.model.Headline
import com.report.news.domain.translator.NewsTranslator.toHeadlineModelList
import com.report.news.domain.usecase.GetHeadlineNewsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WebViewModel  @Inject internal constructor(
) : ViewModel() {

    private val _accessUrl = MutableStateFlow("")
    val url: StateFlow<String> = _accessUrl

    fun setAccessUrl(accessUrl : String){
        viewModelScope.launch(Dispatchers.Main) {
            _accessUrl.emit(accessUrl)
        }
    }


}