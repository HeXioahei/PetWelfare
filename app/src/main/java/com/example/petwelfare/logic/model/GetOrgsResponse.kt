package com.example.petwelfare.logic.model

data class GetOrgsResponse(
    val code: Int,
    val msg: String,
    val data: MutableList<Org>
)

data class Org(
    val collect_nums: Int,
    val collect_status: Int,
    val contact: String,
    val description: String,
    val head_image: String,
    val org_name: String
) {
    constructor() : this(
        0, 0, "aa", "aa", "aa", "aa"
    )
}