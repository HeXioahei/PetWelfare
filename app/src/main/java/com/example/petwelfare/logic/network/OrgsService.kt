package com.example.petwelfare.logic.network

import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.model.GetStrayResponse
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

interface OrgsService {

    @GET("/orgs/")
    fun getOrgs(
        @Header("Authorization") Authorization: String
    ): Call<GetOrgsResponse>

    @GET("/orgs/searching/")
    fun searchOrgs(
        @Query("keywords") keywords: String,
        @Header("Authorization") Authorization: String
    ): Call<GetOrgsResponse>

    @GET("/orgs/{id}/comments/")
    fun getComments(
        @Path("id") id: String
    ): Call<GetCommentsResponse>

    @POST("/orgs/{id}/comments/")
    fun writeComments(
        @Path("id") id: String,
        @Query("comment") comment: String,
        @Query("time") time: String,
        @Query("last_cid") last_cid: Int,
        @Query("level") level: Int
    ): Call<BaseResponse>

    @PUT("/orgs/{id}/collections/")
    fun collect(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>
}