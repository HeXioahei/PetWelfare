package com.example.petwelfare.logic.model

data class GetUserDetailResponse(
    val code: Int,
    val msg: String,
    val data: UserDetail2
)

data class GetFollowsListResponse(
    val code: Int,
    val msg: String,
    var data: Follows
)

data class GetFansListResponse(
    val code: Int,
    val msg: String,
    var data: Fans
)

data class Follows (
    var follows: MutableList<UserBrief>
)

data class Fans (
    var fans: MutableList<UserBrief>
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

    // 积分
    var integral: Int,
    var personality: String,

    // 评分
    var score: Int,

    var telephone: String,
    var username: String,

    // 是否被我关注
    var follow_status: Int,

    var collect_orgs_nums: Int
) {
    constructor() : this(
        "", 0, 0, "", 0L, 0, "", 0, "", "", 0, 0
    )
}

data class UserBrief(
    var head_image: String,
    var name: String,
    var personality: String,

    // 是否被我关注
    var follow_status: Int,

    var id: Long
) {
    constructor() : this("", "", "", 0, 0L)
}

data class UserMostBrief(
    var head_image: String,
    var username: String,

    // 是否被我关注
    var follow_status: Int,

    var id: Long
) {
    constructor() : this("", "", 0, 0L)
}

