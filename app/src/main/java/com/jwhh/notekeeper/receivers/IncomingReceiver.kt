package com.jwhh.notekeeper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast

class IncomingReceiver : BroadcastReceiver() {
    val TAG = IncomingReceiver::class.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {

        Log.i(TAG, "Hello from IncomingReceiver")

        val state = intent?.getStringExtra(TelephonyManager.EXTRA_STATE)


        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
            Toast.makeText(context,"Ringing State",Toast.LENGTH_SHORT).show();
        }

        if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
            Toast.makeText(context,"Received State",Toast.LENGTH_SHORT).show();
        }

        if(state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
            Toast.makeText(context,"IDLE State",Toast.LENGTH_SHORT).show();
        }
    }
}