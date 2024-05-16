package com.example.petwelfare.logic.model

data class LoginResponse(val code: Int, val msg: String, var data: LoginData)

data class LoginData(var access_token: String, var refresh_token: String, var id: Long)