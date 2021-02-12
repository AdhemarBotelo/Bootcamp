package com.jwhh.notekeeper.domain.entities

data class NewsSourcesEntity(
        var status: String? = null,
        var articles: List<NewsPublisherEntity> = emptyList()
)
