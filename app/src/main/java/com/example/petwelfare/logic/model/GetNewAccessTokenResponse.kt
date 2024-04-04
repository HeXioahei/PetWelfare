package com.example.petwelfare.logic.model

data class GetNewAccessTokenResponse (
    val code: Int,
    val data: AccessToken,
    val msg: String
)

data class AccessToken (
    val accessToken: String
)