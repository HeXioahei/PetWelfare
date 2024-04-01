package com.example.petwelfare.logic.model

data class GetCommentsResponse(
    val code: Int,
    val msg: String,
    val data: Comments
)

data class Comments(
    /**
     * 用户在总用户列表中的id
     */
    val aid: Int,

    /**
     * 评论在父评论中的id
     */
    val cid: Int,

    val comment: String,
    val headImage: String,
    val kidComments: MutableList<KidComment>,
    val time: String,
    val username: String
)

data class KidComment(
    /**
     * 评论在子评论中的id
     */
    val cid: Int,

    val comment: String,
    val headImage: String,
    val time: String,
    val username: String
)