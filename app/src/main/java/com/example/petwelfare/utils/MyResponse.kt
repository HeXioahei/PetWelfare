package com.example.petwelfare.utils

import okhttp3.Headers
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.util.Objects

class MyResponse<T>(private var rawResponse: Response, private var body: T?, private var errorBody: ResponseBody?) {

    fun raw(): Response {
        return rawResponse
    }

    fun code(): Int {
        return rawResponse.code
    }

    fun message(): String {
        return rawResponse.message
    }

    fun headers(): Headers {
        return rawResponse.headers
    }

    fun isSuccessful(): Boolean {
        return rawResponse.isSuccessful
    }

    fun body(): T? {
        return body
    }

    fun errorBody(): ResponseBody? {
        return errorBody
    }

    override fun toString(): String {
        return rawResponse.toString()
    }

}