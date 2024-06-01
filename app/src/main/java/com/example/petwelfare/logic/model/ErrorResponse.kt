package com.example.petwelfare.logic.model

import android.view.textclassifier.ConversationActions.Message

class ErrorResponse (
    var code: Int,
    var msg: String,
    var message: String
) {
    constructor() : this(0, "网络请求错误", "网络请求错误")
}