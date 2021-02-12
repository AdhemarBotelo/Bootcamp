package com.jwhh.notekeeper.data.repository

import com.jwhh.notekeeper.data.db.ArticlesDao
import com.jwhh.notekeeper.data.db.NewsDatabase
import com.jwhh.notekeeper.data.entities.NewsDataEntityMapper
import com.jwhh.notekeeper.data.entities.NewsEntityDataMapper
import com.jwhh.notekeeper.domain.entities.NewsSourcesEntity
import io.reactivex.Flowable

class NewsCacheImpl(private val database: NewsDatabase,
                    private val entityToDataMapper: NewsEntityDataMapper,
                    private val dataToEntityMapper: NewsDataEntityMapper) : NewsDataStore {

    private val dao: ArticlesDao = database.getArticlesDao()

    override fun getNews(): Flowable<NewsSourcesEntity> {
        return dao.getAllArticles().map { it ->
            dataToEntityMapper.mapToEntity(it)
        }
    }

    fun saveArticles(it: NewsSourcesEntity) {
        dao.clear()
        dao.saveAllArticles(it.articles.map { articles -> entityToDataMapper.mapArticleToEntity(articles) })
    }

}