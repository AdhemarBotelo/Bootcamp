package com.jwhh.notekeeper.services

import android.app.Service
import android.content.Intent
import android.os.*
import android.widget.Toast
import java.lang.Exception

class MyMessengerService : Service() {


    inner private class IncomingHandler : Handler() {

        override fun handleMessage(messageFromActivity: Message?) {
            if (messageFromActivity != null && messageFromActivity.what == 43) {

                var bundle = messageFromActivity.data
                val numOne = bundle.getInt("numOne")
                val numTwo = bundle.getInt("numTwo")
                var res = addNumber(numOne, numTwo)
                Toast.makeText(getApplicationContext(), "Result : " + res, Toast.LENGTH_SHORT).show();

                // response to activity
                val incommingMEssenger = messageFromActivity.replyTo
                var messageToActivity = Message.obtain(null, 88)

                var bundleMessage = Bundle()
                bundleMessage.putInt("result", res)

                messageToActivity.data = bundleMessage

                try {
                    incommingMEssenger.send(messageToActivity)
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }

            } else {
                super.handleMessage(messageFromActivity)
            }
        }

    }


    val mMessenger = Messenger(IncomingHandler())

    override fun onBind(intent: Intent?): IBinder? {
        return mMessenger.binder
    }


    fun addNumber(a: Int, b: Int): Int {
        return a + b
    }
}