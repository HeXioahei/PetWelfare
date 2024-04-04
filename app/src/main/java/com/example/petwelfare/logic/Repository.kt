package com.example.petwelfare.logic

import androidx.lifecycle.liveData
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.Dispatchers

object Repository {


    fun getUserInfo(id: Long, Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getUserDetailResponse = PetWelfareNetwork.getUserInfo(id, Authorization)
            if (getUserDetailResponse.code == 200) {
                val user = getUserDetailResponse.data
                Result.success(user)
            } else {
                Result.failure(RuntimeException("response code is ${getUserDetailResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure<UserDetail>(e)
        }
        emit(result)
    }

    fun getMyArticles(id: Long, Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getMyArticlesResponse = PetWelfareNetwork.getMyArticles(id, Authorization)
            if (getMyArticlesResponse.code == 200) {
                val articles = getMyArticlesResponse.data
                Result.success(articles)
            } else {
                Result.failure(RuntimeException("response code is ${getMyArticlesResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure<Article>(e)
        }
        emit(result)
    }
}