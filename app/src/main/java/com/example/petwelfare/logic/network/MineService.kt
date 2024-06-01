package com.example.petwelfare.logic.network

import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetFansListResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.GetTalksResponse
import com.example.petwelfare.logic.model.GetFollowsListResponse
import com.example.petwelfare.logic.model.GetUserDetailResponse
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

interface MineService {

    @GET("/users/information/")
    fun getUserInfo(
        @Query("id") id: Long,
        @Header("Authorization") Authorization: String
    ): Call<GetUserDetailResponse>

    @PUT("/users/{id}/follows/")
    fun follow(
        @Path("id") id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @GET("/users/follows/")
    fun getFollows(
        @Header("Authorization") Authorization: String
    ): Call<GetFollowsListResponse>

    @GET("/users/fans/")
    fun getFans(
        @Header("Authorization") Authorization: String
    ): Call<GetFansListResponse>

    @GET("/users/collections/loss/")
    fun getCollectLoss(
        @Header("Authorization") Authorization: String
    ): Call<GetLossResponse>

    @GET("/users/collections/stray/")
    fun getCollectStray(
        @Header("Authorization") Authorization: String
    ): Call<GetStrayResponse>

    @GET("/users/collections/orgs/")
    fun getCollectOrgs(
        @Header("Authorization") Authorization: String
    ): Call<GetOrgsResponse>

    @GET("/users/collections/articles/")
    fun getCollectArticles(
        @Header("Authorization") Authorization: String
    ): Call<GetArticlesResponse>

    @GET("/users/articles/")
    fun getMyArticles(
        @Query("id") id: Long,
        @Header("Authorization") Authorization: String
    ): Call<GetArticlesResponse>

    @GET("/users/loss/")
    fun getMyLoss(
        @Query("id") id: Long,
        @Header("Authorization") Authorization: String
    ): Call<GetLossResponse>

    @GET("/users/stray/")
    fun getMyStray(
        @Query("id") id: Long,
        @Header("Authorization") Authorization: String
    ): Call<GetStrayResponse>

    @DELETE("/users/articles/{article_id}")
    fun delMyArticles(
        @Path("article_id") article_id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @DELETE("/users/loss/{loss_id}")
    fun delMyLoss(
        @Path("loss_id") loss_id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @DELETE("/users/stray/{stray_id}")
    fun delMyStray(
        @Path("stray_id") stray_id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @Multipart
    @PUT("/users/changes/head/")
    fun changeHead(
        @Part headImage: MultipartBody.Part,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/users/changes/username/")
    fun changeUsername(
        @Query("username") username: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/users/changes/address/")
    fun changeAddress(
        @Query("address") address: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/users/changes/telephone/")
    fun changeTelephone(
        @Query("telephone") telephone: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @PUT("/users/changes/personality/")
    fun changePersonality(
        @Query("personality") personality: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @POST("/users/talk/sending/")
    fun sendMessage(
        @Query("send_id") send_id: Long,
        @Query("receive_id") receive_id: Long,
        @Query("message") message: String,
        @Query("time") time: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @GET("/users/talk/receiving/")
    fun receiveMessage(
        @Query("send_id") send_id: Long,
        @Query("receive_id") receive_id: Long,
        @Header("Authorization") Authorization: String
    ): Call<GetTalksResponse>
}