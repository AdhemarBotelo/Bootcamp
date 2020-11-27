package com.jwhh.notekeeper.data

import android.net.Uri
import android.provider.BaseColumns

object NationContract {

    const val CONTENT_AUTHORITY = "com.jwhh.notekeeper.data.NationProvider"

    // content://com.jwhh.notekeeper.data.NationProvider/
    val BASE_CONTENT_URI = Uri.parse("content://$CONTENT_AUTHORITY")
    const val PATH_COUNTRIES = "countries"

    object NationEntry : BaseColumns {

        // content://com.jwhh.notekeeper.data.NationProvider/countries
        val CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COUNTRIES)
        const val TABLE_NAME = "countries"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_CONTINENT = "continent"
    }
}