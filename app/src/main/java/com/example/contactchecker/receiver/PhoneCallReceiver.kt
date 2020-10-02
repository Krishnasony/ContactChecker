package com.example.contactchecker.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.util.Log
import java.util.*


abstract class PhoneCallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        val teleMgr = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val psl: PhoneStateListener = object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String) {
                when (state) {
                    TelephonyManager.CALL_STATE_RINGING -> {
                        Log.i("CallReceiverBroadcast", "Incoming call caught")
                        onIncomingCallStarted(context, incomingNumber, Date())
                    }
                    TelephonyManager.CALL_STATE_IDLE -> {
                        Log.i("CallReceiverBroadcast", "CALL_STATE_IDLE")
                        onIncomingCallEnded(context, incomingNumber, Date(), Date())
                    }
                    TelephonyManager.CALL_STATE_OFFHOOK -> {
                        Log.i("CallReceiverBroadcast", "CALL_STATE_OFFHOOK")
                        onIncomingCallEnded(context, incomingNumber, Date(), Date())
                    }
                }
            }
        }
        teleMgr.listen(psl, PhoneStateListener.LISTEN_CALL_STATE)
        teleMgr.listen(psl, PhoneStateListener.LISTEN_NONE)
    }

    abstract fun onIncomingCallStarted(ctx: Context?, number: String?, start: Date?)
    abstract fun onIncomingCallEnded(ctx: Context?, number: String?, start: Date?, end: Date?)
}