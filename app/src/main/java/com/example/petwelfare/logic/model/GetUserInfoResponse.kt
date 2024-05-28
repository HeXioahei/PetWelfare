package com.example.petwelfare.logic.model

data class GetUserDetailResponse(
    val code: Int,
    val msg: String,
    val data: UserDetail2
)

data class GetUserBriefListResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<UserBrief>
)

data class UserDetail2(
    var author: UserDetail
)

data class UserDetail(
    var address: String,
    var fan_nums: Int,
    var follow_nums: Int,
    var head_image: String,
    var id: Long,
    var integral: Int,
    var personality: String,
    var score: Int,
    var telephone: String,
    var username: String,
    var follow_status: Int
) {
    constructor() : this(
        "aa", 0, 0, "aa", 0L, 0, "aa", 0, "aa", "aa", 0
    )
}

data class UserBrief(
    var head_image: String,
    var username: String,
    var personality: String,
    var follow_status: Int,
    var id: Long
) {
    constructor() : this("aa", "aa", "aa", 0, 0L)
}

data class UserMostBrief(
    var head_image: String,
    var username: String,
    var follow_status: Int,
    var id: Long
) {
    constructor() : this("aa", "aa", 0, 0L)
}

