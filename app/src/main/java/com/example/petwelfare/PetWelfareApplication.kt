package com.example.petwelfare

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class PetWelfareApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var Authorization : String
        var userId: Long = 0
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}