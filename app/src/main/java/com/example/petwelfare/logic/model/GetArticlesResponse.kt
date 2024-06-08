package com.example.petwelfare.logic.model

data class GetArticlesResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Article>
)

data class Article(

    var user: UserMostBrief,

    // 收藏量
    var collect_nums: Int,

    // 收藏状态
    var collect_status: Int,

    var comment_nums: Int,
    var hit_nums: Int,

    // 文章id
    var id: Int,

    // 点赞量
    var like_nums: Int,

    var like_status: Int,
    var media: MutableList<String>,

    // 正文
    var text: String,

    // 发布时间
    var time: String
) {
    constructor() : this(
        UserMostBrief(), 0,0,0,0,0,0,0, mutableListOf("", "", ""), "", ""
    )
}