package com.example.navigationdemo1

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import com.example.navigationdemo1.EditNoteFragmentArgs.fromBundle
import kotlinx.android.synthetic.main.fragment_edit_note.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EditNoteFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EditNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class EditNoteFragment : Fragment() {

    private lateinit var viewModel: EditNoteViewModel

    private val noteId by lazy {
        fromBundle(arguments).noteId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(EditNoteViewModel::class.java)
        viewModel.observableCurrentNote.observe(this, Observer { currentNote ->
            currentNote?.let { initCurrentNote(currentNote) }
        })
        viewModel.observableEditStatus.observe(this, Observer { editStatus ->
            editStatus?.let { render(editStatus) }
        })

        viewModel.initNote(noteId)

        setupEditNoteSubmitHandling()
    }

    private fun initCurrentNote(note: Note) {
        editNoteText.setText(note.text)
    }

    private fun render(editStatus: Boolean) {
        when (editStatus) {
            true -> {
                view?.let {
                    findNavController(it).popBackStack()
                }
            }
            false -> editNoteText.error = getString(R.string.error_validating_note)
        }
    }

    private fun setupEditNoteSubmitHandling() {
        editNoteText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val args = fromBundle(arguments)
                viewModel.editNote(noteId, v.text.toString())
                v.closeSoftKeyboard()
                true
            } else {
                false
            }
        }
    }
}