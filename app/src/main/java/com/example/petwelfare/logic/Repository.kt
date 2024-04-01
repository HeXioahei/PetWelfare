package com.example.petwelfare.logic

import androidx.lifecycle.liveData
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.Dispatchers

object Repository {

    fun getUserInfo(id: Int, Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getUserInfoResponse = PetWelfareNetwork.getUserInfo(id, Authorization)
            if (getUserInfoResponse.code == 200) {
                val user = getUserInfoResponse.data
                Result.success(user)
            } else {
                Result.failure(RuntimeException("response code is ${getUserInfoResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure<UserDetail>(e)
        }
        emit(result)
    }
}