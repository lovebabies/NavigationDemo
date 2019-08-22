package com.example.navigationdemo1

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.example.navigationdemo1.NoteDetailFragmentArgs.fromBundle
import com.example.navigationdemo1.NoteDetailFragmentDirections.actionNoteDetailToDeleteNote
import com.example.navigationdemo1.NoteDetailFragmentDirections.actionNoteDetailToEditNote
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_note_detail.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NoteDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NoteDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class NoteDetailFragment : Fragment() {

    private lateinit var viewModel: NoteDetailViewModel

    private val noteId by lazy {
        fromBundle(arguments).noteId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteDetailViewModel::class.java)
        viewModel.observableNote.observe(this, Observer { note ->
            note?.let { render(note) } ?: renderNoteNotFound()
        })

        editNoteButton.setOnClickListener {
            val navDirections = actionNoteDetailToEditNote(noteId)
            findNavController(it).navigate(navDirections)
        }

        deleteNoteButton.setOnClickListener {
            val navDirections = actionNoteDetailToDeleteNote(noteId)
            findNavController(it).navigate(navDirections)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNote(noteId)
    }

    private fun render(note: Note) {
        noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.text)
    }

    private fun renderNoteNotFound() {
        noteIdView.visibility = View.GONE
        noteText.visibility = View.GONE
        view?.let {
            Snackbar.make(it, R.string.error_loading_note, Snackbar.LENGTH_LONG).show()
        }
    }
}