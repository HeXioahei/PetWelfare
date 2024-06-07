package com.example.petwelfare.logic

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.utils.ActivityCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Repository {

    var networkFailedCount = 0

    val _tokenLiveData = MutableLiveData<String>()
    val tokenLiveData : LiveData<String> = _tokenLiveData

    fun refreshToken() {
        if (refreshToken != "") {
            CoroutineScope(Dispatchers.Main).launch {
                _tokenLiveData.value = PetWelfareNetwork.refreshToken(refreshToken).data.access_token
            }
        } else {
            Toast.makeText(PetWelfareApplication.context, "登陆已过期，请重新登录", Toast.LENGTH_SHORT).show()
            ActivityCollector.removeActivityUntilBegin()
        }
    }

    fun exit() {
        CoroutineScope(Dispatchers.IO).launch {
            MineDao.saveUserId(0L)
            MineDao.saveToken("", "")
            PetWelfareNetwork.exit(Authorization)
            Authorization = ""
            refreshToken = ""
            myId = -1
            ActivityCollector.removeActivityUntilBegin()
        }
    }

    var Authorization : String = ""
    var myId: Long = 0
    var refreshToken : String = ""
    var myDetail: UserDetail = UserDetail()
    var mailbox : String = ""
    var myPetList : MutableList<Pet> = mutableListOf()
    var lazyHeaders: LazyHeaders = LazyHeaders.Builder()
        .addHeader("Authorization", Authorization)
        .build()

    fun getArticles(order: Int, Authorization: String) = liveData(Dispatchers.IO) {
        Log.d("getArticles", "going")
        val result = try {
            val  getArticlesResponse = PetWelfareNetwork.getArticles(order, Authorization)
            if (getArticlesResponse.code == 200) {
                Log.d("getUserInfo", "success")
                val articles = getArticlesResponse.data
                Result.success(articles)
            } else {
                Result.failure(RuntimeException("response code is ${getArticlesResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getUserInfo(id: Long, Authorization: String) = liveData(Dispatchers.IO) {
        Log.d("getUserInfo", "going")
        val result = try {
            val getUserDetailResponse = PetWelfareNetwork.getUserInfo(id, Authorization)
            if (getUserDetailResponse.code == 200) {
                Log.d("getUserInfo", "success")
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

    fun getCollectLoss(Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getLossResponse = PetWelfareNetwork.getCollectLoss(Authorization)
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

    fun getCollectArticles(Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getArticlesResponse = PetWelfareNetwork.getCollectArticles(Authorization)
            if (getArticlesResponse.code == 200) {
                val loss = getArticlesResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getArticlesResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getCollectStary(Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getStrayResponse = PetWelfareNetwork.getCollectStray(Authorization)
            if (getStrayResponse.code == 200) {
                val loss = getStrayResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getStrayResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getCollectOrgs(Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getOrgsResponse = PetWelfareNetwork.getCollectOrgs(Authorization)
            if (getOrgsResponse.code == 200) {
                val loss = getOrgsResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getOrgsResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getPetsInfo(ownerId : Long) = liveData(Dispatchers.IO) {
        val result = try {
            val getPetsInfoResponse = PetWelfareNetwork.getPetsInfo(ownerId)
            if (getPetsInfoResponse.code == 200) {
                val loss = getPetsInfoResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getPetsInfoResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getFans(Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getUserBriefResponse = PetWelfareNetwork.getFans(Authorization)
            if (getUserBriefResponse.code == 200) {
                val loss = getUserBriefResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getUserBriefResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }

    fun getFollows(Authorization: String) = liveData(Dispatchers.IO) {
        val result = try {
            val getUserBriefResponse = PetWelfareNetwork.getFollows(Authorization)
            if (getUserBriefResponse.code == 200) {
                val loss = getUserBriefResponse.data
                Result.success(loss)
            } else {
                Result.failure(RuntimeException("response code is ${getUserBriefResponse.code}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
        emit(result)
    }
}