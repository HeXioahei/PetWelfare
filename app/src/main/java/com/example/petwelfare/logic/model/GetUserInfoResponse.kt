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
    var address: String,
    var fanNums: Int,
    var followNums: Int,
    var headImage: String,
    var id: Long,
    var integral: Int,
    var personality: String,
    var score: Int,
    var telephone: String,
    var username: String
)

data class UserBrief(
    val headImage: String,
    val username: String,
    val personality: String,
    val id: Long
)

data class UserMostBrief(
    val headImage: String,
    val username: String,
    val id: Long
)

