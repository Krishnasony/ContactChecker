package com.example.contactchecker.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.contactchecker.R
import com.example.contactchecker.model.ContactModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_add_dialog.*

private const val INTENT_KEY_CONTACTS = "contact"

class AddDialogFragment : BottomSheetDialogFragment() {

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
            if (mContact!=null && et_nick_name.text.toString().isNotEmpty()){
                mCallback.onSaveClick(mContact!!.copy(nickName = et_nick_name.text.toString().trim()))
                dismissAllowingStateLoss()
            } else Toast.makeText(context, "Please enter nick name", Toast.LENGTH_LONG).show()
        }
        btn_negative.setOnClickListener { dismissAllowingStateLoss() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is DialogFragmentCallback) mCallback = context
        else throw RuntimeException("$context must implement DialogListener")
    }

    override fun getTheme() = R.style.BottomSheetDialog

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
