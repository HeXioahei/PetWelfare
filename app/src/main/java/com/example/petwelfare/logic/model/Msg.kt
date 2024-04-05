package com.example.petwelfare.logic.model

class Msg2(val content: String, val type: Int) {
    companion object {
        const val TYPE_RECEIVE =0
        const val TYPE_SENT = 1
    }
}