package com.example.petwelfare.logic.model

data class GetOrgsResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Org>
)

data class Org(
    val collectNums: Int,
    val collectStatus: Int,
    val contact: String,
    val description: String,
    val headImage: String,
    val orgName: String
)