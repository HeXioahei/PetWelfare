package com.example.petwelfare.logic.model

data class GetArticlesResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Article>
)

data class Article(
    var user: UserMostBrief,
    var collect_nums: Int,
    var collect_status: Int,
    var comment_nums: Int,
    var hit_nums: Int,
    var id: Int,
    var like_nums: Int,
    var like_status: Int,
    var media: MutableList<String>,
    var text: String,
    var time: String
) {
    constructor() : this(
        UserMostBrief(), 0,0,0,0,0,0,0, mutableListOf("aa", "aa", "aa"), "aa", "aa"
    )
}