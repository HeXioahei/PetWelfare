package com.example.petwelfare

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener

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