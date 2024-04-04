package com.example.petwelfare.logic.model

data class LoginResponse(val code: Int, val msg: String, val data: LoginData)

data class LoginData(val accessToken: String, val refreshToken: String, val id: Long)