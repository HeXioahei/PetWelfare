package com.example.petwelfare.logic

import androidx.lifecycle.liveData
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

    var Authorization : String = ""
    var userId: Long = 0
    var refreshToken : String = ""
    lateinit var userDetail: UserDetail

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
            Result.failure(e)
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
            Result.failure(e)
        }
        emit(result)
    }

    fun getMyLoss(id: Long, Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getMyLossResponse = PetWelfareNetwork.getMyLoss(id, Authorization)
            if (getMyLossResponse.code == 200) {
                val loss = getMyLossResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getMyLossResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getMyStray(id: Long, Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getMyStrayResponse = PetWelfareNetwork.getMyStray(id, Authorization)
            if (getMyStrayResponse.code == 200) {
                val stray = getMyStrayResponse.data
                Result.success(stray)
            } else {
                Result.failure(RuntimeException("response code is ${getMyStrayResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun receiveMessage(sendId: Long, receiveId: Long, Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getTalksResponse = PetWelfareNetwork.receiveMessage(sendId, receiveId, Authorization)
            if (getTalksResponse.code == 200) {
                val talks = getTalksResponse.data
                Result.success(talks)
            } else {
                Result.failure(RuntimeException("response code is ${getTalksResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getLoss(address: String, Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getLossResponse = PetWelfareNetwork.getLoss(address, Authorization)
            if (getLossResponse.code == 200) {
                val loss = getLossResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getLossResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}