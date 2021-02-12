package com.jwhh.notekeeper

import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.provider.BaseColumns
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jwhh.notekeeper.data.NationContract
import com.jwhh.notekeeper.data.NationDBHelper
import com.jwhh.notekeeper.databinding.ActivityMyContentProviderBinding
import kotlinx.android.synthetic.main.activity_my_content_provider.*

class MyContentProviderActivity : AppCompatActivity() {

    private val TAG = "MyContentProviderActivity"
    private lateinit var binding: ActivityMyContentProviderBinding
    private lateinit var databaseHelper: NationDBHelper
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMyContentProviderBinding>(this, R.layout.activity_my_content_provider)

        databaseHelper = NationDBHelper(this)
        database = databaseHelper.writableDatabase

        binding.btnInsert.setOnClickListener() {
            insert()
        }

        binding.btnUpdate.setOnClickListener() {
            update()
        }

        binding.btnDelete.setOnClickListener() {
            delete()
        }

        binding.btnQueryByID.setOnClickListener() {
            queryRowById()
        }

        binding.btnDisplayAll.setOnClickListener() {
            queryAndDisplayAll()
        }
    }

    private fun queryRowById() {

        val rowid = binding.etQueryByRowId.text.toString()

        val projection = arrayOf(BaseColumns._ID, NationContract.NationEntry.COLUMN_COUNTRY, NationContract.NationEntry.COLUMN_CONTINENT)
        val selection = "${BaseColumns._ID} = ?"
        // WHERE columnname = "value"
        val selectionArgs = arrayOf(rowid)
        val sortOrder = null

//        val cursor = database.query(NationContract.NationEntry.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs,
//                null,
//                null,
//                sortOrder)

        val uri = Uri.withAppendedPath(NationContract.NationEntry.CONTENT_URI, rowid)
        Log.i(TAG, "" + uri)
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)
        with(cursor) {
            if (cursor != null && moveToNext()) {
                //cursor  -1 0 1 2 3

                var str = ""

                val columns = columnNames
                for (column in columns) {
                    str = str + "\t" + cursor.getString(getColumnIndex(column))
                }
                str += "\n"

                close()
                Log.i(TAG, str)
            }
        }
    }

    private fun delete() {
        var countryName = binding.etWhereToDelete.text.toString()

        val selection = "${NationContract.NationEntry.COLUMN_COUNTRY} = ?"  // WHERE country ="Bolivia"
        val selectionArgs = arrayOf(countryName)

        val rowsDeleted = database.delete(NationContract.NationEntry.TABLE_NAME, selection, selectionArgs)
        Log.i(TAG, "Number of rows deleted: " + rowsDeleted);
    }

    private fun update() {
        var whereCountry = binding.etWhereToUpdate.text.toString()
        var continentName = binding.etUpdateContinent.text.toString()

        val contentValues = ContentValues().apply {
            put(NationContract.NationEntry.COLUMN_CONTINENT, continentName)
        }

        val selection = "${NationContract.NationEntry.COLUMN_COUNTRY} = ?"
        val selectionArgs = arrayOf(whereCountry)

        val rowsUpdated = database.update(NationContract.NationEntry.TABLE_NAME, contentValues, selection, selectionArgs)
        Log.i(TAG, "Number of rows updated: " + rowsUpdated);
    }

    private fun queryAndDisplayAll() {
        val intent = Intent(this, ListNationsActivity::class.java)
        startActivity(intent)

        val projection = arrayOf(BaseColumns._ID, NationContract.NationEntry.COLUMN_COUNTRY, NationContract.NationEntry.COLUMN_CONTINENT)
        val selection = null
        val selectionArgs = null
        val sortOrder = null
//        val cursor = database.query(NationContract.NationEntry.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs, null, null, sortOrder)

        val uri = NationContract.NationEntry.CONTENT_URI
        Log.i(TAG, "" + uri)
        val cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        if (cursor != null) {
            //cursor  -1 0 1 2 3
            with(cursor) {
                var str = ""
                while (moveToNext()) {
                    val columns = columnNames
                    for (column in columns) {
                        str = str + "\t" + cursor.getString(getColumnIndex(column))
                    }
                    str += "\n"
                }
                close()
                Log.i(TAG, str)
            }
        }
    }

    private fun insert() {
        var countryName = binding.etCountry.text.toString()
        var continentName = binding.etContinent.text.toString()

        val contentValues = ContentValues().apply {
            put(NationContract.NationEntry.COLUMN_COUNTRY, countryName)
            put(NationContract.NationEntry.COLUMN_CONTINENT, continentName)
        }

//        val rowId = database.insert(NationContract.NationEntry.TABLE_NAME, null, contentValues)
//        Log.i(TAG, "Items inserted in table with row id: " + rowId);

        val uri = NationContract.NationEntry.CONTENT_URI
        val uriRowInserted = contentResolver.insert(uri, contentValues)
        Log.i(TAG, "Items inserted at: " + uriRowInserted);
    }
}