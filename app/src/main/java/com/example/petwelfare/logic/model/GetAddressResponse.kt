package com.example.petwelfare.logic.model

data class GetAddressResponse (
    val code: Int,
    val msg: String,
    val data: Address
)

data class Address (
    val address: String
)