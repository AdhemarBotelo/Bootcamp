package com.jwhh.notekeeper.data

import android.provider.BaseColumns

object NationContract {

    object NationEntry : BaseColumns {
        const val TABLE_NAME = "countries"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_CONTINENT = "continent"
    }
}