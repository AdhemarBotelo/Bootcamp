package com.jwhh.notekeeper

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.jwhh.notekeeper.databinding.ActivityMainBinding
import com.jwhh.notekeeper.viewmodel.ListCourseNotesActivityViewModel
import com.jwhh.notekeeper.viewmodel.MainViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private val tag = this::class.java.simpleName
    private var notePosition = POSITION_NOT_SET

    val helper = NoteGetLocationHelper(this, lifecycle)

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        buttonSurvey.setOnClickListener {
            startActivity(Intent(this, SurveyActivity::class.java))
        }

        setSupportActionBar(toolbar)

        val adapterCourses = ArrayAdapter<CourseInfo>(this,
                android.R.layout.simple_spinner_item,
                DataManager.courses.values.toList())
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerCourses.adapter = adapterCourses

        notePosition = savedInstanceState?.getInt(NOTE_POSITION, POSITION_NOT_SET)
                ?: intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET)

        if (notePosition != POSITION_NOT_SET)
            displayNote()
        else {
            createNote()
        }

        Log.d(tag, "OnCreate")
    }

    private fun createNote() {
        notePosition = viewModel.createNewNote()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(NOTE_POSITION, notePosition)
    }

    private fun displayNote() {
        val note = DataManager.notes[notePosition]
        viewModel.displayNote(notePosition)
        Log.i(tag, "Displaying not for position $notePosition")
        val coursePosition = DataManager.courses.values.indexOf(note.course)
        spinnerCourses.setSelection(coursePosition)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_next -> {
                helper.sendMessage(DataManager.notes[notePosition])
                moveNext()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun moveNext() {
        ++notePosition
        displayNote()
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if (notePosition >= DataManager.notes.lastIndex) {
            Log.i(tag, "Cambio de icono $notePosition max value of notes ${DataManager.notes.lastIndex}")
            val menuItem = menu?.findItem(R.id.action_next)
            if (menuItem != null) {
                menuItem.icon = getDrawable(R.drawable.ic_block_white_24dp)
                menuItem.isEnabled = false
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onPause() {
        super.onPause()
        saveNote()
        Log.d(tag, "OnPause")
    }

    private fun saveNote() {
        viewModel.updateNote(notePosition, spinnerCourses.selectedItem as CourseInfo)
    }
}











