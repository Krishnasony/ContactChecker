package com.example.contactchecker.view

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.contactchecker.R
import com.example.contactchecker.utils.setIcon
import com.example.contactchecker.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_phone_call_dialog.*

const val INTENT_KEY_PHONE = "phone_no"
@AndroidEntryPoint
class PhoneCallDialog : AppCompatActivity() {

    private val mViewModel: ContactViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setFinishOnTouchOutside(true)
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_phone_call_dialog)
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            window.setGravity(Gravity.TOP)
            val phoneNo: String? = intent.extras!!.getString(INTENT_KEY_PHONE)
            phoneNo?.let { it ->
                mViewModel.getContactByNumber(it)
                mViewModel.contact.observe(this, { contact ->
                    contact?.apply {
                        tv_phone_number.text = number
                        tv_email.text = email ?: name
                        tv_name.text = nickName
                        iv_caller.setIcon(name ?: number)
                        iv_close.setOnClickListener {
                            finish()
                        }
                    } ?: run {
                        finish()
                    }
                })
            } ?: run {
                finish()
            }

        } catch (e: Exception) {
            Log.d("Exception", e.toString())
            e.printStackTrace()
        }
    }
}