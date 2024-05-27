package com.example.petwelfare.logic.model

data class GetArticlesResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Article>
)

data class Article(
    var user: UserMostBrief,
    var collectNums: Int,
    var collectStatus: Int,
    var commentNums: Int,
    var hitNums: Int,
    var id: Int,
    var likeNums: Int,
    var likeStatus: Int,
    var media: MutableList<String>,
    var text: String,
    var time: String
) {
    constructor() : this(
        UserMostBrief(), 0,0,0,0,0,0,0, mutableListOf("aa", "aa", "aa"), "aa", "aa"
    )
}