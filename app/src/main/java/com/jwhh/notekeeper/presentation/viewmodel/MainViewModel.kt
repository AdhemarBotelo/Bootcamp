package com.jwhh.notekeeper.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.jwhh.notekeeper.presentation.CourseInfo
import com.jwhh.notekeeper.data.DataManager
import com.jwhh.notekeeper.presentation.NoteInfo

class MainViewModel : ViewModel() {

    private val _noteInfo: MutableLiveData<NoteInfo> = MutableLiveData<NoteInfo>()
    private val _likes = MutableLiveData<Int>()

    init {
        _likes.value = 0
    }

    var likes: LiveData<Int> = _likes

    val popularity: LiveData<Popularity> = Transformations.map(_likes) {
        when {
            it > 9 -> Popularity.STAR
            it > 4 -> Popularity.POPULAR
            else -> Popularity.NORMAL
        }
    }

    fun onLike() {
        _likes.value = (_likes.value ?: 0) + 1
    }

    val noteInfo: LiveData<NoteInfo>
        get() = _noteInfo

    fun displayNote(notePosition: Int) {
        _noteInfo.value = DataManager.notes[notePosition]
    }

    fun createNewNote(): Int {
        val note = NoteInfo()
        _noteInfo.value = note
        DataManager.notes.add(note)
        return DataManager.notes.lastIndex
    }

    fun updateNote(notePosition:Int,course: CourseInfo){
        val note = DataManager.notes[notePosition]
        note.title = _noteInfo.value?.title
        note.text = _noteInfo.value?.text
        note.course = course
    }

}

enum class Popularity {
    NORMAL,
    POPULAR,
    STAR
}