package com.example.petwelfare.logic.model

data class GetStrayResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Stray>
)

data class Stray(
    // 发现地址
    var address: String,
    var id: Int,
    var collect_status: Int,
    var collect_nums: Int,
    var comments_nums: Int,
    var description: String,
    var photos: MutableList<String>,

    // 发布时间
    var time: String,

    // 发布者
    var user: UserMostBrief
) {
    constructor() : this(
        "aa", 0, 0, 0, 0,
        "aa", mutableListOf(), "aa", UserMostBrief()
    )
}