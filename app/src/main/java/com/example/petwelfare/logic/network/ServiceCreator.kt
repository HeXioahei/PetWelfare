package com.example.petwelfare.logic.network

import android.net.sip.SipErrorCode.TIME_OUT
import com.example.petwelfare.utils.TokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceCreator {
    private const val BASE_URL = "http://47.115.212.55:8000"

    private val okHttpClient = OkHttpClient().newBuilder().apply {
        addInterceptor(TokenInterceptor())
        retryOnConnectionFailure(true)
    }.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttpClient)
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //inline fun <reified T> create(): T = create(T::class.java)
}