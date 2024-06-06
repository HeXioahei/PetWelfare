package com.example.petwelfare.logic.model

data class GetOrgsResponse(
    val code: Int,
    val msg: String,
    val data: Orgs
)

data class Orgs(
    var org_list: MutableList<Org>
)

data class Org(
    var collect_nums: Int,
    var collect_status: Int,
    var id : Int,
    var contract: String,
    var description: String,
    var head_image: String,
    var org_name: String
) {
    constructor() : this(
        0, 0, -1,  "", "", "", ""
    )
}