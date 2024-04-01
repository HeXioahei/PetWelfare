package com.example.petwelfare.logic.model

data class GetStrayResponse(
    val code: Int,
    val msg: String,
    val data: Stray
)

data class Stray(
    // 发现地址
    val address: String,

    val collcetStatus: Int,
    val collectNums: Int,
    val commentsNums: Int,
    val description: String,
    val photos: String,

    // 发布时间
    val time: String,

    // 发布者
    val user: UserMostBrief
)