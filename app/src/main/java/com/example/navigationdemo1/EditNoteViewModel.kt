package com.example.navigationdemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author: Jayden Nguyen
 * Created date: 8/22/2019
 */
class EditNoteViewModel : ViewModel() {
    private val currentNote = MutableLiveData<Note>()

    private val editStatus = MutableLiveData<Boolean>()

    val observableCurrentNote: LiveData<Note>
        get() = currentNote

    val observableEditStatus: LiveData<Boolean>
        get() = editStatus

    fun editNote(id: Int, noteText: String) {
        editStatus.value = try {
            NoteManager.editNote(id, noteText)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun initNote(id: Int) {
        currentNote.value = NoteManager.getNote(id)
    }
}