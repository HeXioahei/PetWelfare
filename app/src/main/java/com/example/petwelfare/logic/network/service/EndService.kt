package com.example.petwelfare.logic.network.service

import com.example.petwelfare.logic.model.BaseResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST

interface EndService {

    @POST("/users/exit/")
    fun exit(
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>
}