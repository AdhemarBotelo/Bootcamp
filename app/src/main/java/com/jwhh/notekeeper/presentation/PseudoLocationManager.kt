package com.jwhh.notekeeper.presentation

import android.content.Context
import android.os.Looper
import android.util.Log

class PseudoLocationManager(private val content: Context,
                            private val callback: (Double, Double) -> Unit) : Runnable {
    private val tag = this::class.simpleName

    private val latitudes = doubleArrayOf(
            40.9,
            176.9,
            59.74)

    private val longitudes = doubleArrayOf(
            40.9,
            176.9,
            59.74)

    private var locationIndex = 0

    private val callbackMilliseconds = 300L

    private var enable = false
    private val postHandler = android.os.Handler(Looper.getMainLooper())

    fun start() {
        enable = true
        Log.d(tag, "Location manager started")
        triggerCallbackAndScheduleNext()
    }

    fun stop() {
        enable = false
        postHandler.removeCallbacks(this)
        Log.d(tag, "Location manager stopped")
    }

    private fun triggerCallbackAndScheduleNext() {
        callback(latitudes[locationIndex], longitudes[locationIndex])
        locationIndex = (locationIndex + 1) % latitudes.size
        if (enable)
            postHandler.postDelayed(this, callbackMilliseconds)

    }

    override fun run() {
        triggerCallbackAndScheduleNext()
    }
}