package com.jwhh.notekeeper.presentation.news

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.jwhh.notekeeper.domain.common.Mapper
import com.jwhh.notekeeper.domain.entities.NewsSourcesEntity
import com.jwhh.notekeeper.domain.usecases.GetNewsUseCase
import com.jwhh.notekeeper.presentation.common.BaseViewModel
import com.jwhh.notekeeper.presentation.entities.Data
import com.jwhh.notekeeper.presentation.entities.NewsSources
import com.jwhh.notekeeper.presentation.entities.Status
import com.jwhh.notekeeper.presentation.entities.Error

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase,
                    private val mapper: Mapper<NewsSourcesEntity, NewsSources>) : BaseViewModel() {

    companion object {
        private val TAG = "viewmodel"
    }

    var mNews = MutableLiveData<Data<NewsSources>>()

    fun fetchNews() {
        val disposable = getNewsUseCase.getNews()
                .flatMap { mapper.Flowable(it) }
                .subscribe({ response ->
                    Log.d(TAG, "On Next Called")
                    mNews.value = Data(responseType = Status.SUCCESSFUL, data = response)
                }, { error ->
                    Log.d(TAG, "On Error Called")
                    mNews.value = Data(responseType = Status.ERROR, error = Error(error.message))
                }, {
                    Log.d(TAG, "On Complete Called")
                })

        addDisposable(disposable)
    }

    fun getNewsLiveData() = mNews
}