package com.example.petwelfare.utils

import retrofit2.Call
import retrofit2.Response

interface MyCallBack<T> {

    fun onResponse(call: Call<T>?, response: MyResponse<T>?)

    fun onFailure(call: Call<T>?, t: Throwable?)
}