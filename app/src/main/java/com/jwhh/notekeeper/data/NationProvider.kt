package com.jwhh.notekeeper.data

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import android.util.Log

class NationProvider : ContentProvider() {
    val TAG = "NationProvider"
    private lateinit var databaseHelper: NationDBHelper

    var uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    val COUNTRIES = 1
    val COUNTRIES_ID = 2
    val COUNTRIES_COUNTRY_NAME = 3

    init {
        // content://com.jwhh.notekeeper.data.NationProvider/countries
        uriMatcher.addURI(NationContract.CONTENT_AUTHORITY, NationContract.PATH_COUNTRIES, COUNTRIES)
        // content://com.jwhh.notekeeper.data.NationProvider/countries/#
        uriMatcher.addURI(NationContract.CONTENT_AUTHORITY, NationContract.PATH_COUNTRIES + "/#", COUNTRIES_ID)
        // content://com.jwhh.notekeeper.data.NationProvider/countries/*
        uriMatcher.addURI(NationContract.CONTENT_AUTHORITY, NationContract.PATH_COUNTRIES + "/*", COUNTRIES_COUNTRY_NAME)

    }

    override fun onCreate(): Boolean {
        databaseHelper = NationDBHelper(context)
        return true;
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val database = databaseHelper.readableDatabase;
        var cursor: Cursor? = null;
        when (uriMatcher.match(uri)) {
            COUNTRIES -> {
                cursor = database.query(NationContract.NationEntry.TABLE_NAME, projection, null, null, null, null, sortOrder)
            }
            COUNTRIES_ID -> {
                var MySelection = "BaseColumns._ID = ?"
                var MySelectionArgs = arrayOf(ContentUris.parseId(uri).toString())
                cursor = database.query(NationContract.NationEntry.TABLE_NAME, projection, MySelection, MySelectionArgs, null, null, sortOrder)
            }
            else -> throw IllegalArgumentException("Unknow URI $uri")
        }
        return cursor;
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        when (uriMatcher.match(uri)) {
            COUNTRIES -> {
                return insertRecord(uri, values, NationContract.NationEntry.TABLE_NAME)
            }
            else -> throw IllegalArgumentException("Unknow URI $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        TODO("Not yet implemented")
    }

    private fun insertRecord(uri: Uri, values: ContentValues?, tableName: String): Uri? {
        val database = databaseHelper.writableDatabase
        val rowId = database.insert(tableName, null, values)
        if (rowId == -1L) {
            Log.e(TAG, "Insert error for URI " + uri);
            return null;
        }

        // content://com.jwhh.notekeeper.data.NationProvider/countries/1
        return ContentUris.withAppendedId(uri, rowId)
    }
}