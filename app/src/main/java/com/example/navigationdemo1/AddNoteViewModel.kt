package com.example.navigationdemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Author: Jayden Nguyen
 * Created date: 8/22/2019
 */
class AddNoteViewModel : ViewModel() {
    private val status = MutableLiveData<Boolean>()

    val observableStatus: LiveData<Boolean>
        get() = status

    fun addNote(noteText: String) {
        status.value = try {
            NoteManager.addNote(noteText)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }
}