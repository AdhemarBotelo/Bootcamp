package com.jwhh.notekeeper

import androidx.test.rule.ActivityTestRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matchers.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import com.jwhh.notekeeper.data.DataManager
import com.jwhh.notekeeper.presentation.CourseInfo
import com.jwhh.notekeeper.presentation.ListCourseNotesActivity

@RunWith(AndroidJUnit4::class)
class CreateNewNoteTest{
    @Rule @JvmField
    val noteListActivity = ActivityTestRule(ListCourseNotesActivity::class.java)

    @Test
    fun createNewNote(){
        val course = DataManager.courses["android_async"]
        val noteTile = "Test note title"
        val noteText ="This is the body of our note"

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.spinnerCourses)).perform(click())
        onData(allOf(instanceOf(CourseInfo::class.java), equalTo(course))).perform(click())


        onView(withId(R.id.textNoteTitle)).perform(typeText(noteTile))
        onView(withId(R.id.textNoteText)).perform(typeText(noteText), closeSoftKeyboard())

        pressBack()

        val newNote = DataManager.notes.last()
        assertEquals(course,newNote.course)
        assertEquals(noteTile,newNote.title)
        assertEquals(noteText,newNote.text)

    }
}