package com.jwhh.notekeeper.presentation.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class MyBoundService : Service() {

    private val myLocalBinder: MyBinder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): MyBoundService? {
            return this@MyBoundService
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return myLocalBinder
    }

    fun add(a: Int, b: Int): Int {
        return a + b
    }

    fun subtract(a: Int, b: Int): Int {
        return a - b
    }

    fun multiply(a: Int, b: Int): Int {
        return a * b
    }

    fun divide(a: Int, b: Int): Float {
        return a.toFloat() / b.toFloat()
    }
}