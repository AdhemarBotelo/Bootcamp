package com.jwhh.notekeeper.data.repository

import com.jwhh.notekeeper.domain.entities.NewsSourcesEntity
import io.reactivex.Flowable

interface NewsDataStore{
    fun getNews(): Flowable<NewsSourcesEntity>
}