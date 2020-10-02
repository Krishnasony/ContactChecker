package com.example.contactchecker.receiver

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.contactchecker.view.INTENT_KEY_PHONE
import com.example.contactchecker.view.PhoneCallDialog
import java.util.*

class CallReceiver : PhoneCallReceiver() {
    var context: Context? = null
    override fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?) {
        Toast.makeText(ctx, "Incoming Call from $number", Toast.LENGTH_LONG).show()
        context = ctx
        val intent = Intent(context, PhoneCallDialog::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra(INTENT_KEY_PHONE, number)
        Handler(Looper.getMainLooper()).postDelayed({ context?.startActivity(intent) }, 2000)
    }

    override fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?) {
        Toast.makeText(ctx, "Call dropped$number", Toast.LENGTH_LONG).show()
    }

}