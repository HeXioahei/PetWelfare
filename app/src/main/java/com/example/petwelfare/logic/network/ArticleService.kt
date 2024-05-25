package com.example.petwelfare.logic.network

import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetCommentsResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Query

interface ArticleService {

    @GET("/articles/")
    fun getArticles(
        @Query("order") order: Int,
        @Header("Authorization") Authorization: String
    ): Call<GetArticlesResponse>

    @GET("/articles/searching/")
    fun searchArticles(
        @Query("keywords") keywords: String,
        @Header("Authorization") Authorization: String
    ): Call<GetArticlesResponse>

    @Multipart
    @POST("/articles/")
    fun writeArticle(
        @Query("time") time: String,
        @Query("text") text: String,
        @Header("Authorization") Authorization: String,
        @PartMap photo_list: Map<String, RequestBody>
//        @Part photo_list: MultipartBody.Part
    ): Call<BaseResponse>

    @GET("/articles/{id}/comments/")
    fun getComments(
        @Path("id") id: String
    ): Call<GetCommentsResponse>

    @POST("/articles/{id}/comments/")
    fun writeComments(
        @Path("id") id: String,
        @Query("comment") comment: String,
        @Query("time") time: String,
        @Query("last_cid") last_cid: Int,
        @Query("level") level: Int
    ): Call<BaseResponse>

    @PUT("/articles/{id}/hits/")
    fun hit(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/articles/{id}/likes/")
    fun like(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/articles/{id}/collections/")
    fun collect(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>
}