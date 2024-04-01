package com.example.petwelfare.logic.model

data class GetTalksResponse (
    val code: Long,
    val data: Talks,
    val msg: String
)

data class Talks (
    val talks: MutableList<Talk>
)

data class Talk (
    // 该信息所属者在总用户列表中的id
    val id: Int,

    // 信息内容
    val messgae: String,

    // 该条信息的时间
    val time: String
)