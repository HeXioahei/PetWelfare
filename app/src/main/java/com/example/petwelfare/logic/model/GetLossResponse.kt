package com.example.petwelfare.logic.model

data class GetLossResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Loss>
)

data class Loss(
    // 走失地点
    var address: String,
    var collectNums: Int,
    var collectStatus: Int,
    var commentNums: Int,
    var contact: String,
    var description: String,
    var id: Int,

    // 走失时间
    var lostTime: String,

    var name: String,
    var photos: List<String>,

    // 发布时间
    var sendTime: String,

    var sex: Int,
    var type: String,

    // 发布者
    var user: UserMostBrief
) {
    constructor() : this(
        "aa", 0, 0, 0, "aa", "aa", 0,
        "aa", "aa", listOf(" ", " ", " "), "aa", 0, "aa", UserMostBrief()
    )
}

