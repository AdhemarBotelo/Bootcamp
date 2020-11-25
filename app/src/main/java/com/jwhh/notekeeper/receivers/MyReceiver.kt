package com.jwhh.notekeeper.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MyReceiver : BroadcastReceiver() {

    val TAG = MyReceiver::class.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {

        var a = intent?.getIntExtra("a", 0)
        var b = intent?.getIntExtra("b", 0)

        var sum = 0
        if (a != null && b != null) {
            sum = a + b
        }

        var returnIntent = Intent("my.result.intent")
        returnIntent.putExtra("sum", sum)

        val lbm = LocalBroadcastManager.getInstance(context!!)
        lbm.sendBroadcast(returnIntent)
    }
}