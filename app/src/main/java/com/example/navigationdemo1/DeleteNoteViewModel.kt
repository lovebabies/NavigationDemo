package com.example.navigationdemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author: Jayden Nguyen
 * Created date: 8/22/2019
 */
class DeleteNoteViewModel : ViewModel() {
    private val currentNote = MutableLiveData<Note>()

    private val deleteStatus = MutableLiveData<Boolean>()

    val observableCurrentNote: LiveData<Note>
        get() = currentNote

    val observableDeleteStatus: LiveData<Boolean>
        get() = deleteStatus

    fun deleteNote(id: Int) {
        deleteStatus.value = try {
            NoteManager.deleteNote(id)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun initNote(id: Int) {
        currentNote.value = NoteManager.getNote(id)
    }
}