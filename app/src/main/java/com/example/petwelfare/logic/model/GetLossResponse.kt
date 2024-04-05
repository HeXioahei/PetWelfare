package com.example.petwelfare.logic.model

data class GetLossResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Loss>
)

data class Loss(
    // 走失地点
    val address: String,
    val collectNums: Int,
    val collectStatus: Int,
    val commentNums: Int,
    val contract: String,
    val description: String,
    val id: Int,

    // 走失时间
    val lostTime: String,

    val name: String,
    val photos: List<String>,

    // 发布时间
    val sendTime: String,

    val sex: String,
    val type: String,

    // 发布者
    val user: UserMostBrief
)

