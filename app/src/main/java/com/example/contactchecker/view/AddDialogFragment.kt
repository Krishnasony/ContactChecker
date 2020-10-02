package com.example.contactchecker.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.contactchecker.R
import com.example.contactchecker.model.ContactModel
import kotlinx.android.synthetic.main.fragment_add_dialog.*

private const val INTENT_KEY_CONTACTS = "contact"

class AddDialogFragment : DialogFragment() {

    private var mContact: ContactModel? = null
    private lateinit var mCallback: DialogFragmentCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            bundle.getParcelable<ContactModel>(INTENT_KEY_CONTACTS)?.let {
                mContact = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_positive.setOnClickListener {
            mContact?.let {
                mCallback.onSaveClick(it)
            }
        }
        btn_negative.setOnClickListener { dismiss() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialogFragmentCallback) mCallback = context
        else throw RuntimeException("$context must implement DialogListener")
    }

    companion object {

        @JvmStatic
        fun newInstance(model: ContactModel) = AddDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(INTENT_KEY_CONTACTS, model)
            }
        }
    }

    interface DialogFragmentCallback {

        fun onSaveClick(contactModel: ContactModel)

    }

}
