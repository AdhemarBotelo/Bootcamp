package com.jwhh.notekeeper.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class NationDBHelper(context: Context?)
    : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private val SQL_CREATE_CUNRTRY_TABLE =
            "CREATE TABLE ${NationContract.NationEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${NationContract.NationEntry.COLUMN_COUNTRY} TEXT," +
                    "${NationContract.NationEntry.COLUMN_CONTINENT} TEXT)"

    private val SQL_DELETE_COUNTRY_TABLE = "DROP TABLE IF EXIST " + NationContract.NationEntry.TABLE_NAME

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "nations.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_CUNRTRY_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //remove all rows an recreate the table
        db?.execSQL(SQL_DELETE_COUNTRY_TABLE)
        onCreate(db)
    }

    /*
		TABLE NAME: countries	Database Name: nations.db

		 _id	country		continent
 		  1		 India		 Asia
 		  2		 Japan		 Asia
 		  3		 Nigeria	 Africa
* */
}