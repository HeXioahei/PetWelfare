package com.example.petwelfare.logic.model

data class BaseResponse(
    var code: Int,
    var msg: String
) {
    constructor() : this(0, "")
}
