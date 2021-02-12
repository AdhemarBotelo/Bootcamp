package com.jwhh.notekeeper.presentation

import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.os.Bundle
import android.provider.BaseColumns
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.appcompat.app.AppCompatActivity
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.data.NationContract

@Suppress("DEPRECATION")
class ListNationsActivity : AppCompatActivity(),android.app.LoaderManager.LoaderCallbacks<android.database.Cursor>{

    private var simpleCursorAdapter: SimpleCursorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_nations)

        loaderManager.initLoader(10, null, this)

        val from = arrayOf<String>(NationContract.NationEntry.COLUMN_COUNTRY, NationContract.NationEntry.COLUMN_CONTINENT)
        val to = intArrayOf(R.id.txvCountryName, R.id.txvContinentName)

        simpleCursorAdapter = SimpleCursorAdapter(this, R.layout.item_nation_list, null, from, to, 0)

        val listView = findViewById<ListView>(R.id.listView)
        listView.adapter = simpleCursorAdapter
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {

        val projection = arrayOf(BaseColumns._ID, NationContract.NationEntry.COLUMN_COUNTRY, NationContract.NationEntry.COLUMN_CONTINENT)
        val selection = null
        val selectionArgs = null
        val sortOrder = null

        val uri = NationContract.NationEntry.CONTENT_URI
        // returns a CursorLoader object that carries a Cursor Object.
        // The cursor Object contains all rows queried from database using ContentProvider.

        return CursorLoader(this,uri, projection, selection, selectionArgs, sortOrder)
    }

    override fun onLoadFinished(loader: Loader<Cursor>?, cursor: Cursor?) {
        simpleCursorAdapter!!.swapCursor(cursor)
    }

    override fun onLoaderReset(loader: Loader<Cursor>?) {
        simpleCursorAdapter!!.swapCursor(null)
    }
}