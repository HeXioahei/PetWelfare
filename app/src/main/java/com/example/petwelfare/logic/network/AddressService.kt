package com.example.petwelfare.logic.network

import com.example.petwelfare.logic.model.GetAddressResponse
import retrofit2.Call
import retrofit2.http.GET

interface AddressService {

    @GET("/address")
    fun getAddressDefault() : Call<GetAddressResponse>
}