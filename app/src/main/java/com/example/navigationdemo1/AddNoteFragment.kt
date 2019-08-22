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
import kotlinx.android.synthetic.main.fragment_add_note.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddNoteFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddNoteFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class AddNoteFragment : Fragment() {

    private lateinit var viewModel: AddNoteViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)
        viewModel.observableStatus.observe(this, Observer { status ->
            status?.let { render(status) }
        })

        addNoteText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.addNote(v.text.toString())
                v.closeSoftKeyboard()
                true
            } else {
                false
            }
        }
    }

    private fun render(status: Boolean) {
        when (status) {
            true -> {
                view?.let {
                    findNavController(it).popBackStack()
                }
            }
            false -> addNoteText.error = getString(R.string.error_validating_note)
        }
    }
}