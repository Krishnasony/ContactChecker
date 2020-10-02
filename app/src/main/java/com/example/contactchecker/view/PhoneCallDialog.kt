package com.example.contactchecker.view

import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.contactchecker.R
import kotlinx.android.synthetic.main.activity_phone_call_dialog.*

const val INTENT_KEY_PHONE = "phone_no"
class PhoneCallDialog : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setFinishOnTouchOutside(false)
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_phone_call_dialog)
            val phoneNo:String? = intent.extras!!.getString(INTENT_KEY_PHONE)
            tv_phone_number.text = phoneNo?:""

        } catch (e: Exception) {
            Log.d("Exception", e.toString())
            e.printStackTrace()
        }
    }
}