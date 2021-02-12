package com.jwhh.notekeeper.presentation

import android.Manifest
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.jwhh.notekeeper.R
import com.jwhh.notekeeper.databinding.ActivityMyBroadcastReceiverBinding
import com.jwhh.notekeeper.presentation.receivers.MyFourthReceiver
import com.jwhh.notekeeper.presentation.receivers.MyReceiver

class MyBroadcastReceiverActivity : AppCompatActivity() {

    lateinit var binding: ActivityMyBroadcastReceiverBinding

    val TAG = MyBroadcastReceiverActivity::class.simpleName
    private lateinit var mLocalBRManager: LocalBroadcastManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMyBroadcastReceiverBinding>(this, R.layout.activity_my_broadcast_receiver)

        binding.buttonSendNormal.setOnClickListener() {
//            val intent = Intent(this, MyThirdReceiverInner::class.java)
            Intent().also { intent ->
                intent.setAction("my.custom.action.name")
//                sendBroadcast(intent)
                val myBundle = Bundle()
                myBundle.putString("title", "SmartDeveloper")
                sendOrderedBroadcast(intent, null, MyFourthReceiver(), null, Activity.RESULT_OK, "Android", myBundle)
            }
            Log.i("MyFirstReceiver", "afeter send broadcas" + Thread.currentThread().name)
        }

        binding.buttonBradcastExterior.setOnClickListener() {
            val intent = Intent("my_action_exterior")
            sendBroadcast(intent, "my.own.permission")
        }

        binding.buttonLocalBroadcast.setOnClickListener() {
            val intent = Intent(this, MyReceiver::class.java)
            intent.putExtra("a", 10)
            intent.putExtra("b", 20)
            sendBroadcast(intent)
        }
        mLocalBRManager = LocalBroadcastManager.getInstance(this)

        val permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            val permissionsRequired: Array<String> = Array(1, { Manifest.permission.READ_PHONE_STATE })
            ActivityCompat.requestPermissions(this, permissionsRequired, 17)
        }

    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("my.result.intent")
        mLocalBRManager.registerReceiver(resultReciever, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        mLocalBRManager.unregisterReceiver(resultReciever)
    }

    private val resultReciever = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val sum = intent?.getIntExtra("sum", 0)
            binding.textViewSum.setText(sum.toString())
            Toast.makeText(context, "Sum is " + sum, Toast.LENGTH_LONG).show();
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            17 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission Granted")
                } else {
                    Log.i(TAG, "Permission Denied")
                }
            }
        }
    }


    class MyThirdReceiverInner : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            if (isOrderedBroadcast) {
                val initCode = resultCode
                val initData = resultData
                var bundle = getResultExtras(true)
                val title = bundle.getString("title")

                Log.i("MyThirdReceiverInner", "Code: $initCode Data:  $initData  Bundle:  $title");
                Toast.makeText(context, "Hello from 3rd Receiver", Toast.LENGTH_LONG).show();

                resultCode = 17
                resultData = "Ios"
                bundle.putString("title", "WiseDeveloper")
                setResultExtras(bundle)
            }
        }
    }
}