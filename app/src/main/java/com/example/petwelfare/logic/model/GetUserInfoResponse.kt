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
    var username: String,
    var followStatus: Int
) {
    constructor() : this(
        "aa", 0, 0, "aa", 0L, 0, "aa", 0, "aa", "aa", 0
    )
}

data class UserBrief(
    var headImage: String,
    var username: String,
    var personality: String,
    var followStatus: Int,
    var id: Long
) {
    constructor() : this("aa", "aa", "aa", 0, 0L)
}

data class UserMostBrief(
    var headImage: String,
    var username: String,
    var followStatus: Int,
    var id: Long
) {
    constructor() : this("aa", "aa", 0, 0L)
}

