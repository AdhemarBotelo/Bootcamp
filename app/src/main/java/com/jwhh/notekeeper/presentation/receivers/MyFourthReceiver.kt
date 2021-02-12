package com.jwhh.notekeeper.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyFourthReceiver : BroadcastReceiver() {

    val TAG = MyFourthReceiver::class.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isOrderedBroadcast) {
            val initCode = resultCode
            val initData = resultData
            var bundle = getResultExtras(true)
            val title = bundle.getString("title")

            Log.i(TAG, "Code: $initCode Data:  $initData  Bundle:  $title");
            Toast.makeText(context, "Hello from 4th Receiver", Toast.LENGTH_LONG).show();
        }
    }
}