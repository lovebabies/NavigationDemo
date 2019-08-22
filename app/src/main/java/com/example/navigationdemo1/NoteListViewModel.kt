package com.example.navigationdemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author: Jayden Nguyen
 * Created date: 8/22/2019
 */
class NoteListViewModel : ViewModel() {
    private val noteList = MutableLiveData<List<Note>>()

    val observableNoteList: LiveData<List<Note>>
        get() = noteList

    init {
        load()
    }

    fun load() {
        noteList.value = NoteManager.getNoteList()
    }
}