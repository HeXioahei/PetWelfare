package com.example.petwelfare.logic.network.service

import android.util.Log
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetNewAccessTokenResponse
import com.example.petwelfare.logic.model.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface BeginService {

    @POST("/users/login/")
    fun login(
        @Query("mailbox") mailbox: String,
        @Query("password") password: String
    ): Call<LoginResponse>

    @POST("/verifications/emails/{verify_type}")
    fun getVerification(
        @Path("verify_type") verify_type: String,
        @Query("mailbox") mailbox: String
    ): Call<BaseResponse>

    @POST("/users/register/")
    fun register(
        @Query("mailbox") mailbox: String,
        @Query("password") password: String,
        @Query("verification") verification: String
    ): Call<BaseResponse>

    @PUT("/users/changes/password/")
    fun resetPassword(
        @Query("mailbox") mailbox: String,
        @Query("password") password: String,
        @Query("verification") verification: String
    ): Call<BaseResponse>


    @GET("/users/refreshtoken")
    fun refreshToken(
        @Header("Authorization") Authorization: String
    ): Call<GetNewAccessTokenResponse>
}