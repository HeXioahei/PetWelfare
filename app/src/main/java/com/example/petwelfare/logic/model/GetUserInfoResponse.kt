package com.example.petwelfare.logic.model

data class GetUserDetailResponse(
    val code: Int,
    val msg: String,
    val data: UserDetail
)

data class GetUserBriefResponse(
    val code: Int,
    val msg: String,
    val data: UserBrief
)

data class UserDetail(
    val address: String,
    val fanNums: Int,
    val followNums: Int,
    val headImage: String,
    val id: Int,
    val integral: Int,
    val personality: String,
    val score: Int,
    val telephone: String,
    val username: String
)

data class UserBrief(
    val headImage: String,
    val username: String,
    val personality: String,
    val id: Int
)

data class UserMostBrief(
    val headImage: String,
    val username: String,
    val id: Int
)

