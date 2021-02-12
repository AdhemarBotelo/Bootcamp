package com.jwhh.notekeeper.presentation.mappers

import com.jwhh.notekeeper.domain.common.Mapper
import com.jwhh.notekeeper.domain.entities.NewsPublisherEntity
import com.jwhh.notekeeper.domain.entities.NewsSourcesEntity
import com.jwhh.notekeeper.presentation.entities.NewsPublisher
import com.jwhh.notekeeper.presentation.entities.NewsSources

class NewsEntityMapper : Mapper<NewsSourcesEntity, NewsSources>() {
    override fun mapFrom(data: NewsSourcesEntity): NewsSources = NewsSources(
            status = data?.status,
            articles = mapListArticlesToPresetation(data?.articles)
    )

    private fun mapListArticlesToPresetation(articles: List<NewsPublisherEntity>?)
            : List<NewsPublisher> = articles?.map { mapArticleToPresentation(it) }
            ?: emptyList()

    private fun mapArticleToPresentation(response: NewsPublisherEntity): NewsPublisher = NewsPublisher(
            id = response.id,
            name = response.name,
            description = response.description,
            url = response.url,
            category = response.category
    )
}