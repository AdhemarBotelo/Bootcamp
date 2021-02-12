package com.jwhh.notekeeper.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jwhh.notekeeper.data.entities.NewsPublisherData

@Database(entities = [NewsPublisherData::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun getArticlesDao(): ArticlesDao
}