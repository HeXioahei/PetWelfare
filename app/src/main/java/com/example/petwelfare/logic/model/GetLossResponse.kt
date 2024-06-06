package com.example.petwelfare.logic.model

data class GetLossResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Loss>
)

data class Loss(
    // 走失地点
    var address: String,
    var collect_nums: Int,
    var collect_status: Int,
    var comment_nums: Int,
    var contact: String,
    var description: String,
    var id: Int,

    // 走失时间
    var lost_time: String,

    var name: String,
    var photos: List<String>,

    // 发布时间
    var send_time: String,

    var sex: Int,
    var type: String,

    // 发布者
    var user: UserMostBrief
) {
    constructor() : this(
        "福建省 福州市", 0, 0, 0, "", "", 0,
        "", "", listOf(" ", " ", " "), "", 0, "", UserMostBrief()
    )
}

