package com.example.petwelfare.logic.model

data class GetCommentsResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Comments>
)

data class Comments(
    /**
     * 用户在总用户列表中的id
     */
    val aid: Long,

    /**
     * 评论在父评论中的id
     */
    val cid: Int,

    val comment: String,
    val head_image: String,
    val kid_comments: MutableList<KidComment>,
    val time: String,
    val username: String
) {
    constructor() : this(
        0L,0, "aa","aa",
        mutableListOf(KidComment(), KidComment(), KidComment(),KidComment(),KidComment()),"aa","aa"
    )
}

data class KidComment(
    /**
     * 评论在子评论中的id
     */
    val cid: Int,

    val comment: String,
    val head_image: String,
    val time: String,
    val username: String,
    val aid: Long
) {
    constructor():this(
        0,"aa","aa","aa","aa", -1L
    )
}