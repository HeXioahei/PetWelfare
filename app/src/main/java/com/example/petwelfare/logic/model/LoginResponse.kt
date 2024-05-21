package com.example.petwelfare.logic.model

import java.lang.Error

data class LoginResponse(val code: Int, val msg: String, var data: LoginData) {

//    constructor() : this(0, "请求失败", LoginData())
//    fun e(t: Throwable) {
//
//    }
}

data class LoginData(var access_token: String, var refresh_token: String, var id: Long) {
//    constructor() : this("", "", 0L)
}