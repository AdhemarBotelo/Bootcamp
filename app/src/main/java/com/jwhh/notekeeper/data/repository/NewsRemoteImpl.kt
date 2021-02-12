package com.jwhh.notekeeper.data.repository

import com.jwhh.notekeeper.data.api.RemoteNewsApi
import com.jwhh.notekeeper.data.entities.NewsDataEntityMapper
import com.jwhh.notekeeper.domain.entities.NewsSourcesEntity
import io.reactivex.Flowable

class NewsRemoteImpl constructor(private val api: RemoteNewsApi) : NewsDataStore {

    private val newsMapper = NewsDataEntityMapper()

    override fun getNews(): Flowable<NewsSourcesEntity> {
        return api.getNews().map { newsMapper.mapToEntity(it) }
    }
}