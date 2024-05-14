package com.example.petwelfare.logic.model

data class GetArticlesResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Article>
)

data class Article(
    val user: UserMostBrief,
    val collectNums: Int,
    val collectStatus: Int,
    val commentNums: Int,
    val hitNums: Int,
    val id: Int,
    val likeNums: Int,
    val likeStatus: Int,
    val media: MutableList<String>,
    val text: String,
    val time: String
) {
    constructor() : this(
        UserMostBrief(), 0,0,0,0,0,0,0, mutableListOf(), "aa", "aa"
    )
}