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
import com.example.navigationdemo1.DeleteNoteFragmentArgs.fromBundle
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_delete_note.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DeleteNoteFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DeleteNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DeleteNoteFragment : Fragment() {

    private lateinit var viewModel: DeleteNoteViewModel

    private val noteId by lazy {
        fromBundle(arguments).noteId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_delete_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DeleteNoteViewModel::class.java)
        viewModel.observableCurrentNote.observe(this, Observer { currentNote ->
            currentNote?.let { initCurrentNote(currentNote) }
        })
        viewModel.observableDeleteStatus.observe(this, Observer { deleteStatus ->
            deleteStatus?.let { render(deleteStatus) }
        })

        viewModel.initNote(noteId)

        cancelDeleteButton.setOnClickListener {
            findNavController(it).popBackStack()
        }

        confirmDeleteButton.setOnClickListener {
            viewModel.deleteNote(noteId)
        }
    }

    private fun initCurrentNote(note: Note) {
        noteIdView.text = String.format(getString(R.string.note_detail_id), note.id)
        noteText.text = String.format(getString(R.string.note_detail_text), note.text)
    }

    private fun render(deleteStatus: Boolean) {
        when (deleteStatus) {
            true -> {
                view?.let {
                    findNavController(it).popBackStack(R.id.noteListFragment, false)
                }
            }
            false -> Snackbar.make(confirmDeleteButton, R.string.error_deleting_note, Snackbar.LENGTH_LONG).show()
        }
    }
}
