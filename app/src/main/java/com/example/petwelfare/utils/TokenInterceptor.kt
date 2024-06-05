package com.example.petwelfare.utils

import android.util.Log
import android.widget.Toast
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("intercept", "intercept")
        val request = chain.request()
        var response = chain.proceed(request)

        if (response.code == 403) {
            CoroutineScope(Dispatchers.IO).launch {
                Repository.Authorization = PetWelfareNetwork.refreshToken(Repository.refreshToken).data.access_token
                if (Repository.Authorization!="") {
                    MineDao.saveToken(Repository.Authorization, "")
                }
            }
        }
//        if (response.body != null) {
//            Log.d("responsettbody", response.body.toString())
//            Log.d("responsettcode", response.code.toString())
//            Log.d("responsettmessage", response.message)
//            return response
//        } else {
//            Log.d("responsetttt", response.body.toString())
//            if (response.code == 403) {
//                Log.d("403", "403")
//                if (Repository.refreshToken != "") {
//                    Log.d("4033", "4033")
//                    CoroutineScope(Dispatchers.IO).launch {
//                        Log.d("40333", "40333")
//                        Repository.Authorization = PetWelfareNetwork.refreshToken(Repository.refreshToken).data.access_token
//                        Repository.refreshToken = ""
//                        // 重新构建新的 token 请求
//                        val builder = request.url.newBuilder().setEncodedQueryParameter("Authorization", Repository.Authorization)
//                        val newRequest = request.newBuilder().method(request.method, request.body)
//                            .url(builder.build()).build()
//                        response = chain.proceed(newRequest)
////                        PetWelfareNetwork.refreshToken(Repository.Authorization)
//                    }
//                } else {
//                    Toast.makeText(PetWelfareApplication.context, "登陆已过期，请重新登录", Toast.LENGTH_SHORT).show()
//                    ActivityCollector.removeActivityUntilLogin()
//                }
//            }
//        }

        return response
    }

    private fun refreshToken() {
        CoroutineScope(Dispatchers.IO).launch {
            Repository.Authorization = PetWelfareNetwork.refreshToken(Repository.refreshToken).data.access_token
            Repository.refreshToken = ""
        }
    }
}