package com.example.contactchecker.receiver

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.contactchecker.view.INTENT_KEY_PHONE
import com.example.contactchecker.view.PhoneCallDialog
import java.util.*
import kotlin.system.exitProcess

class CallReceiver : PhoneCallReceiver() {

    override fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Toast.makeText(ctx, "Incoming Call from $number", Toast.LENGTH_LONG).show()
        showCallerDialog(ctx, number)
    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
    }

    override fun onOutgoingCallStarted(ctx: Context?, number: String?) {
        showCallerDialog(ctx, number)
    }

    private fun showCallerDialog(context: Context?, number: String?) {
        val intent = Intent(context, PhoneCallDialog::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
        intent.putExtra(INTENT_KEY_PHONE, number)
        Handler(Looper.getMainLooper()).postDelayed({ context?.startActivity(intent) }, 2000)
    }

}