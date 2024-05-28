package com.example.petwelfare.logic.model

data class GetTalksResponse (
    val code: Int,
    val data: Talks,
    val msg: String
)

data class Talks (
    val talks: MutableList<Msg>
)

data class Msg (
    // 该信息所属者在总用户列表中的id
    val id: Long,

    // 信息内容
    val message: String,

    // 该条信息的时间
    val time: String
) {
    companion object {
        const val TYPE_RECEIVE = 0
        const val TYPE_SENT = 1
    }
}