package com.example.navigationdemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author: Jayden Nguyen
 * Created date: 8/22/2019
 */
class NoteDetailViewModel : ViewModel() {
    private val note = MutableLiveData<Note>()

    val observableNote: LiveData<Note>
        get() = note

    fun getNote(id: Int) {
        note.value = NoteManager.getNote(id)
    }
}