package com.example.petwelfare.logic.network

import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetPetsInfoResponse
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

interface PetsService {

    @GET("/pets/information/")
    fun getPetsInfo(
        @Query("owner_id") ownerId: Long
    ): Call<GetPetsInfoResponse>

    @POST("/pets/register/")
    @Multipart
    fun addPets(
        @Query("name") name: String,
        @Query("sex") sex: String,
        @Query("type") type: String,
        @Query("birthday") birthday: String,
        @Query("description") description: String,
        @Header("Authorization") Aurhorization: String,
        @Part head_image: MultipartBody.Part
    ): Call<BaseResponse>

    @PUT("/pets/changes/head/")
    fun changeHead(
        @Query("pet_id") pet_id: Int,
        @Part head_image: MultipartBody.Part,
        @Header("Authorization") Aurhorization: String
    ): Call<BaseResponse>

    @PUT("/pets/changes/name/")
    fun changeName(
        @Query("pet_id") pet_id: Int,
        @Query("name") name: String,
        @Header("Authorization") Aurhorization: String
    ): Call<BaseResponse>

    @PUT("/pets/changes/sex/")
    fun changeSex(
        @Query("pet_id") pet_id: Int,
        @Query("sex") sex: Int,
        @Header("Authorization") Aurhorization: String
    ): Call<BaseResponse>

    @PUT("/pets/changes/type/")
    fun changeType(
        @Query("pet_id") pet_id: Int,
        @Query("type") type: String,
        @Header("Authorization") Aurhorization: String
    ): Call<BaseResponse>

    @PUT("/pets/changes/birthday/")
    fun changeBirthday(
        @Query("pet_id") pet_id: Int,
        @Query("birthday") birthday: String,
        @Header("Authorization") Aurhorization: String
    ): Call<BaseResponse>

    @PUT("/pets/changes/description/")
    fun changeDescription(
        @Query("pet_id") pet_id: Int,
        @Query("description") description: String,
        @Header("Authorization") Aurhorization: String
    ): Call<BaseResponse>

    @DELETE("/pets/information/{pet_id}")
    fun delPet(
        @Path("pet_id") pet_id: String,
        @Header("Authorization") Authorization: String
    ): Call<BaseResponse>

    @Multipart
    @PUT("/pets/photos/")
    fun addPicture(
        @Query("pet_id") pet_id: Int,
        @Part photos : List<MultipartBody.Part>,
        @Header("Authorization") Aurhorization: String
    ) : Call<BaseResponse>
}