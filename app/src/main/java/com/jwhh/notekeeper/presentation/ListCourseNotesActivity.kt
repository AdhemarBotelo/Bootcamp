package com.jwhh.notekeeper.presentation

import androidx.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.navigation.ui.AppBarConfiguration
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.presentation.adapters.CourseRecyclerAdapter
import com.jwhh.notekeeper.presentation.adapters.NoteRecylcerAdapter
import com.jwhh.notekeeper.data.DataManager
import com.jwhh.notekeeper.presentation.news.NewsActivity
import com.jwhh.notekeeper.presentation.viewmodel.ListCourseNotesActivityViewModel
import kotlinx.android.synthetic.main.activity_list_course_notes.*
import kotlinx.android.synthetic.main.content_list_course_notes.*

class ListCourseNotesActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val noteLayoutManager by lazy {
        LinearLayoutManager(this)
    }

    private val noteRecylcerAdapter by lazy {
        NoteRecylcerAdapter(this, DataManager.notes)
    }

    private val courseLayoutManager by lazy {
        GridLayoutManager(this, resources.getInteger(R.integer.course_grid_span))
    }
    private val courseRecyclerAdapter by lazy {
        CourseRecyclerAdapter(this, DataManager.courses.values.toList())
    }

    private val viewModel: ListCourseNotesActivityViewModel by lazy {
        ViewModelProviders.of(this).get(ListCourseNotesActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_course_notes)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val activityIntent = Intent(this, MainActivity::class.java)
            startActivity(activityIntent)
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)

        handleDisplaySelection(viewModel.navDrawerDisplaySelection)

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)
    }

    private fun displayNotes() {
        recyclerListNotes.layoutManager = noteLayoutManager
        recyclerListNotes.adapter = noteRecylcerAdapter
    }

    private fun displayCourses() {
        recyclerListNotes.layoutManager = courseLayoutManager
        recyclerListNotes.adapter = courseRecyclerAdapter
    }

    override fun onResume() {
        super.onResume()
        recyclerListNotes.adapter?.notifyDataSetChanged()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_course,
            R.id.nav_notes -> {
                handleDisplaySelection(item.itemId)
                viewModel.navDrawerDisplaySelection = item.itemId
            }
            R.id.nav_dogs -> {
                val activityIntent = Intent(this, ListDogActivity::class.java)
                startActivity(activityIntent)
            }
            R.id.nav_news -> {
                startActivity(Intent(this, NewsActivity::class.java))
            }
            R.id.nav_trivia -> {
                startActivity(Intent(this, SurveyActivity::class.java))
            }
            R.id.nav_services -> {
                val intent = Intent(this, MyServiceActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_receivers -> {
                val intent = Intent(this, MyBroadcastReceiverActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_contentProvider -> {
                val intent = Intent(this, MyContentProviderActivity::class.java)
                startActivity(intent)
            }
            R.id.action_count_note_courses -> {
                val message = getString(R.string.how_many_course_message, DataManager.notes.size, DataManager.courses.size)
                Snackbar.make(recyclerListNotes, message, Snackbar.LENGTH_LONG).show()
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun handleDisplaySelection(itemId: Int) {
        when (itemId) {
            R.id.nav_notes -> {
                displayNotes()
            }
            R.id.nav_course -> {
                displayCourses()
            }
        }
    }


    private fun handleSelection(stringId: Int) {
        Snackbar.make(recyclerListNotes, stringId, Snackbar.LENGTH_LONG).show()
    }
}