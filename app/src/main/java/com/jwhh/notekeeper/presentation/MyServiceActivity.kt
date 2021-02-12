package com.jwhh.notekeeper.presentation

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.databinding.ActivityMyServiceBinding
import com.jwhh.notekeeper.presentation.services.MyBoundService
import com.jwhh.notekeeper.presentation.services.MyMessengerService
import java.lang.Exception


class MyServiceActivity : AppCompatActivity() {

    var isBound = false
    private var myBoundService: MyBoundService? = null
    lateinit var mService: Messenger


    inner private class IncommingResponseHandler : Handler() {

        override fun handleMessage(messageFromService: Message?) {
            if (messageFromService != null && messageFromService.what == 88) {

                var bundle = messageFromService.data
                var resul = bundle.getInt("result", 0)
                binding.textViewResult.setText(resul.toString())
            } else {
                super.handleMessage(messageFromService)
            }
        }
    }

    private val incomingMessenger = Messenger(IncommingResponseHandler())

    lateinit var binding: ActivityMyServiceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityMyServiceBinding>(this, R.layout.activity_my_service)

        binding.buttonAdd.setOnClickListener() {
            binding.textViewResult.setText(myBoundService?.add(4, 6).toString())
        }

        binding.buttonDivider.setOnClickListener() {
            binding.textViewResult.setText(myBoundService?.divide(4, 6).toString())
        }

        binding.buttonStartBoundService.setOnClickListener() {
            val intent = Intent(this, MyMessengerService::class.java)
            bindService(intent, mConnection, BIND_AUTO_CREATE)
        }

        binding.buttonStopBoundService.setOnClickListener() {
            if (isBound) {
                unbindService(mConnection)
                isBound = false
            }
        }

        binding.buttonAddMessenger.setOnClickListener() {

            var msgToService = Message.obtain(null, 43)

            var bundle = Bundle()
            bundle.putInt("numOne", 10)
            bundle.putInt("numTwo", 20)

            msgToService.data = bundle
            msgToService.replyTo = incomingMessenger

            try {
                mService.send(msgToService)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }

        }
    }

    private val mConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mService = Messenger(service)
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(this, MyBoundService::class.java)
        bindService(intent, _serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
        if (isBound) {
            unbindService(_serviceConnection)
            isBound = false
        }
    }

    private val _serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val myLocalBinder: MyBoundService.MyBinder = service as MyBoundService.MyBinder
            myBoundService = myLocalBinder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }

    }
}


