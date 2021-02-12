package com.jwhh.notekeeper.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MySecondReceiver : BroadcastReceiver() {

    val TAG = MyFirstReceiver::class.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isOrderedBroadcast) {
            val initCode = resultCode
            val initData = resultData
            var bundle = getResultExtras(true)
            val title = bundle.getString("title")

            Log.i("MySecondReceiver", "Code: $initCode Data:  $initData  Bundle:  $title");
            Toast.makeText(context, "Hello from 2nd Receiver", Toast.LENGTH_LONG).show();

            bundle.putString("title", "CleverDeveloper")
            setResultExtras(bundle)
            setResult(47, "Blackberry", bundle)
//            abortBroadcast()
        }
    }
}
