package com.example.petwelfare.logic.model

data class GetUserDetailResponse(
    val code: Int,
    val msg: String,
    val data: UserDetail
)

data class GetUserBriefResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<UserBrief>
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
    var headImage: String,
    var username: String,
    var personality: String,
    var id: Long
)

data class UserMostBrief(
    var headImage: String,
    var username: String,
    var id: Long
)

