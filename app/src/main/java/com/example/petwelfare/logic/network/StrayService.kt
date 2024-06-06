package com.example.petwelfare.logic.network

import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.GetStraySearchResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface StrayService {

    @GET("/stray/")
    fun getStray(
        @Query("address") address: String,
        @Header("Authorization") Authorization: String
    ): Call<GetStrayResponse>

    @GET("/stray/searching/")
    fun searchStray(
        @Query("keywords") keywords: String,
        @Header("Authorization") Authorization: String
    ): Call<GetStraySearchResponse>

    @Multipart
    @POST("/stray/")
    fun sendStray(
        @Query("address") address: String,
        @Query("time") time: String,
        @Query("description") description: String,
        @Header("Authorization") Authorization: String,
        @Part photo_list: List<MultipartBody.Part>
    ): Call<BaseResponse>

    @GET("/stray/{id}/comments/")
    fun getComments(
        @Path("id") id: String
    ): Call<GetCommentsResponse>

    @POST("/stray/{id}/comments/")
    fun writeComments(
        @Path("id") id: String,
        @Query("comment") comment: String,
        @Query("time") time: String,
        @Query("last_cid") last_cid: Int,
        @Query("level") level: Int,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/stray/{id}/collections/")
    fun collect(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @DELETE("/stray/{id}")
    fun adopt(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>
}