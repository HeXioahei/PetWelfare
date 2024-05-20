package com.example.petwelfare.logic.network

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
    suspend fun getVerification(
        @Path("verify_type") verify_type: String,
        @Query("mailbox") mailbox: String
    ): BaseResponse

    @POST("/users/register/")
    suspend fun register(
        @Query("mailbox") mailbox: String,
        @Query("password") password: String,
        @Query("verification") verification: String
    ): BaseResponse

    @PUT("/users/changes/password/")
    suspend fun resetPassword(
        @Query("mailbox") mailbox: String,
        @Query("password") password: String,
        @Query("verification") verification: String
    ): BaseResponse

    @GET("/users/refreshtoken")
    fun refreshToken(
        @Header("Authorization") Authorization: String
    ): Call<GetNewAccessTokenResponse>
}