package com.example.petwelfare.logic.network.service

import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.model.GetLossResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface LossService {

    @GET("/loss/")
    fun getLoss(
        @Query("address") address: String,
        @Header("Authorization") Authorization: String
    ): Call<GetLossResponse>

    @GET("/loss/searching/")
    fun searchLoss(
        @Query("keywords") keywords: String,
        @Header("Authorization") Authorization: String
    ): Call<GetLossResponse>

    @Multipart
    @POST("/loss/")
    fun sendLoss(
        @Query("name") name: String,
        @Query("sex") sex: Int,
        @Query("type") type: String,
        @Query("address") address: String,
        @Query("contact") contact: String,
        @Query("lost_time") lost_time: String,
        @Query("send_time") send_time: String,
        @Query("description") description: String,
        @Header("Authorization") Authorization: String,
        @Part photo_list: List<MultipartBody.Part>
    ): Call<BaseResponse>

    @GET("/loss/{id}/comments/")
    fun getComments(
        @Path("id") id: String
    ): Call<GetCommentsResponse>

    @POST("/loss/{id}/comments/")
    fun writeComments(
        @Path("id") id: String,
        @Query("comment") comment: String,
        @Query("time") time: String,
        @Query("last_cid") last_cid: Int,
        @Query("level") level: Int,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/loss/{id}/collections/")
    fun collect(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>
}