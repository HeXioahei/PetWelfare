package com.example.petwelfare

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner

class PetWelfareApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}