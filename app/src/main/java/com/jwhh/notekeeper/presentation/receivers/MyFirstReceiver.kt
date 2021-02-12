package com.jwhh.notekeeper.presentation.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class MyFirstReceiver : BroadcastReceiver() {

    val TAG = MyFirstReceiver::class.simpleName

    override fun onReceive(context: Context?, intent: Intent?) {
        if (isOrderedBroadcast) {
            val initCode = resultCode
            val initData = resultData
            var bundle = getResultExtras(true)
            val title = bundle.getString("title")

            Log.i(TAG, "Code: $initCode Data:  $initData  Bundle:  $title");
            Toast.makeText(context, "Hello from 1st Receiver", Toast.LENGTH_LONG).show();

            bundle.putString("title", "LazyDeveloper")
            setResultExtras(bundle)
            setResult(83, "Windows", bundle)
        }
    }
}