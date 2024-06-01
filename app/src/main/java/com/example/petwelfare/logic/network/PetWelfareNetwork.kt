package com.example.petwelfare.logic.network

import android.util.Log
import android.widget.Toast
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.model.ErrorResponse
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

object PetWelfareNetwork {

    private val beginService = ServiceCreator.create(BeginService::class.java)
    private val mineService = ServiceCreator.create(MineService::class.java)
    private val articlesService = ServiceCreator.create(ArticleService::class.java)
    private val lossService = ServiceCreator.create(LossService::class.java)
    private val strayService = ServiceCreator.create(StrayService::class.java)
    private val orgsService = ServiceCreator.create(OrgsService::class.java)
    private val endService = ServiceCreator.create(EndService::class.java)
    private val petsService = ServiceCreator.create(PetsService::class.java)
    private val addressService = ServiceCreator.create(AddressService::class.java)

    suspend fun getAddressDefault() = addressService.getAddressDefault().await()

    // 开始页
    suspend fun login(mailbox: String, password: String) = beginService
        .login(mailbox, password)
        .await()

    suspend fun getVerification(verifyType: String, mailbox: String) = beginService
        .getVerification(verifyType, mailbox)
        .await()

    suspend fun register(mailbox: String, password: String, verification: String) = beginService
        .register(mailbox, password, verification)
        .await()

    suspend fun resetPassword(mailbox: String, password: String, verification: String) = beginService
        .resetPassword(mailbox, password, verification)
        .await()

    suspend fun refreshToken(Authorization: String) = beginService
        .refreshToken(Authorization)
        .await()

    // 退出登录
    suspend fun exit(Authorization: String) = endService.exit(Authorization).await()

    // 个人页
    suspend fun getUserInfo(id: Long, Authorization: String) = mineService
        .getUserInfo(id, Authorization)
        .await()

    suspend fun follow(id: String, Authorization: String) = mineService
        .follow(id, Authorization)
        .await()

    // 获取列表
    // 私密列表
    suspend fun getFollows(Authorization: String) = mineService
        .getFollows(Authorization)
        .await()

    suspend fun getFans(Authorization: String) = mineService
        .getFans(Authorization)
        .await()

    suspend fun getCollectArticles(Authorization: String) = mineService
        .getCollectArticles(Authorization)
        .await()

    suspend fun getCollectLoss(Authorization: String) = mineService
        .getCollectLoss(Authorization)
        .await()

    suspend fun getCollectStray(Authorization: String) = mineService
        .getCollectStray(Authorization)
        .await()

    suspend fun getCollectOrgs(Authorization: String) = mineService
        .getCollectOrgs(Authorization)
        .await()

    // 公开列表
    suspend fun getMyArticles(id: Long, Authorization: String) = mineService
        .getMyArticles(id, Authorization)
        .await()

    suspend fun getMyLoss(id: Long, Authorization: String) = mineService
        .getMyLoss(id, Authorization)
        .await()

    suspend fun getMyStray(id: Long, Authorization: String) = mineService
        .getMyStray(id, Authorization)
        .await()

    // 删除列表
    suspend fun delMyArticles(id: String, Authorization: String) = mineService
        .delMyArticles(id, Authorization)
        .await()

    suspend fun delMyLoss(id: String, Authorization: String) = mineService
        .delMyLoss(id, Authorization)
        .await()

    suspend fun delMyStray(id: String, Authorization: String) = mineService
        .delMyStray(id, Authorization)
        .await()

    // 修改个人资料
    suspend fun changeHead(headImage: MultipartBody.Part, Authorization: String) = mineService
        .changeHead(headImage, Authorization)
        .await()

    suspend fun changeUsername(username: String, Authorization: String) = mineService
        .changeUsername(username, Authorization)
        .await()

    suspend fun changeAddress(address: String, Authorization: String) = mineService
        .changeAddress(address, Authorization)
        .await()

    suspend fun changeTelephone(telephone: String, Authorization: String) = mineService
        .changeTelephone(telephone, Authorization)
        .await()

    suspend fun changePersonality(personality: String, Authorization: String) = mineService
        .changePersonality(personality, Authorization)
        .await()

    // 私聊
    suspend fun sendMessage(
        sendId: Long,
        receiveId: Long,
        message: String,
        time: String,
        Authorization: String
    ) = mineService
        .sendMessage(sendId, receiveId, message, time, Authorization)
        .await()

    suspend fun receiveMessage(sendId: Long, receiveId: Long, Authorization: String) = mineService
        .receiveMessage(sendId, receiveId, Authorization)
        .await()

    // 宠物页
    suspend fun getPetsInfo(ownerId: Long) = petsService.getPetsInfo(ownerId).await()

    suspend fun addPets(
        name: String,
        sex: String,
        type: String,
        birthday: String,
        description: String,
        Authorization: String,
        headImage: MultipartBody.Part
    ) = petsService
        .addPets(name, sex, type, birthday, description, Authorization, headImage)
        .await()

    suspend fun changePetHead(
        petId: Int, headImage: MultipartBody.Part, Authorization: String
    ) = petsService
        .changeHead(petId, headImage, Authorization)
        .await()

    suspend fun changePetName(
        petId: Int, name: String, Authorization: String
    ) = petsService
        .changeName(petId, name, Authorization)
        .await()

    suspend fun changePetSex(
        petId: Int, sex: String, Authorization: String
    ) = petsService
        .changeSex(petId, sex, Authorization)
        .await()

    suspend fun changePetType(
        petId: Int, type: String, Authorization: String
    ) = petsService
        .changeType(petId, type, Authorization)
        .await()

    suspend fun changePetBirthday(
        petId: Int, birthday: String, Authorization: String
    ) = petsService
        .changeBirthday(petId, birthday, Authorization)
        .await()

    suspend fun changePetDescription(
        petId: Int, description: String, Authorization: String
    ) = petsService
        .changeDescription(petId, description, Authorization)
        .await()

    suspend fun delPet(petId: String, Authorization: String) = petsService
        .delPet(petId, Authorization)
        .await()

    // 日常推文页
    suspend fun getArticles(order :Int, Authorization: String) = articlesService
        .getArticles(order, Authorization)
        .await()

    suspend fun searchArticles(keywords: String, Authorization: String) = articlesService
        .searchArticles(keywords, Authorization)
        .await()

    suspend fun writeArticle(
        time: String, text: String, Authorization: String, photo_list: List<MultipartBody.Part>
    ) = articlesService
        .writeArticle(time, text, Authorization, photo_list)
        .await()

    suspend fun getCommentsInArticles(id: String) = articlesService
        .getComments(id)
        .await()

    suspend fun writeCommentsInArticles(
        id: String, comment: String, time: String, lastCid: Int, level: Int, Authorization: String
    ) = articlesService
        .writeComments(id, comment, time, lastCid, level, Authorization)
        .await()

    suspend fun hitInArticles(id: String, Authorization: String) = articlesService
        .hit(id, Authorization)
        .await()

    suspend fun likeInArticles(id: String, Authorization: String) = articlesService
        .like(id, Authorization)
        .await()

    suspend fun collectInArticles(id: String, Authorization: String) = articlesService
        .collect(id, Authorization)
        .await()

    // 走失动物页
    suspend fun getLoss(address: String, Authorization: String) = lossService
        .getLoss(address, Authorization)
        .await()

    suspend fun searchLoss(keywords: String, Authorization: String) = lossService
        .searchLoss(keywords, Authorization)
        .await()

    suspend fun sendLoss(
        name: String,
        sex: Int,
        type: String,
        address: String,
        contact: String,
        lostTime: String,
        sendTime: String,
        description: String,
        Authorization: String,
        photo_list: List<MultipartBody.Part>
    ) = lossService
        .sendLoss(
            name, sex, type, address, contact, lostTime, sendTime, description, Authorization, photo_list
        )
        .await()

    suspend fun getCommentsInLoss(id: String) = lossService
        .getComments(id)
        .await()

    suspend fun writeCommentsInLoss(
        id: String, comment: String, time: String, lastCid: Int, level: Int, Authorization: String
    ) = lossService
        .writeComments(id, comment, time, lastCid, level, Authorization)
        .await()

    suspend fun collectInLoss(id: String, Authorization: String) = lossService
        .collect(id, Authorization)
        .await()

    // 流浪动物页
    suspend fun getStray(address: String, Authorization: String) = strayService
        .getStray(address, Authorization)
        .await()

    suspend fun searchStray(keywords: String, Authorization: String) = strayService
        .searchStray(keywords, Authorization)
        .await()

    suspend fun sendStray(
        address: String,
        time: String,
        description: String,
        Authorization: String,
        photo_list: List<MultipartBody.Part>
    ) = strayService
        .sendStray(
            address, time, description, Authorization, photo_list
        )
        .await()

    suspend fun adopt(id: String, Authorization: String) = strayService
        .adopt(id, Authorization)
        .await()

    suspend fun getCommentsInStray(id: String) = strayService
        .getComments(id)
        .await()

    suspend fun writeCommentsInStray(
        id: String, comment: String, time: String, lastCid: Int, level: Int, Authorization: String
    ) = strayService
        .writeComments(id, comment, time, lastCid, level ,Authorization)
        .await()

    suspend fun collectInStray(id: String, Authorization: String) = strayService
        .collect(id, Authorization)
        .await()

    // 热心组织页
    suspend fun getOrgs(Authorization: String) = orgsService
        .getOrgs(Authorization)
        .await()

    suspend fun searchOrgs(keywords: String, Authorization: String) = orgsService
        .searchOrgs(keywords, Authorization)
        .await()

    suspend fun getCommentsInOrgs(id: String) = orgsService
        .getComments(id)
        .await()

    suspend fun writeCommentsInOrgs(
        id: String, comment: String, time: String, lastCid: Int, level: Int
    ) = orgsService
        .writeComments(id, comment, time, lastCid, level)
        .await()

    suspend fun collectInOrgs(id: String, Authorization: String) = orgsService
        .collect(id, Authorization)
        .await()

    private suspend fun <T> Call<T>.await(): T {
        Log.d("goRequest","yes")
        return suspendCoroutine { continuation ->
            Log.d("enterCoroutine","yes")
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    Log.d("response", "success")
                    val body = response.body()
                    Log.d("body", body.toString())
                    val errorBody = response.errorBody()
                    if (body != null) {
                        continuation.resume(body)
                    } else {
                        val errorBodyString = errorBody?.string()   // 是string()，而不是toString()
                        val errorResponse = Gson().fromJson(errorBodyString, ErrorResponse::class.java)
                        Log.d("response.body()", response.body().toString())
                        Log.d("response.errorBody()", response.errorBody().toString())
                        Log.d("code", response.code().toString())
                        Log.d("errorResponse.msg", errorResponse.msg)
                        Log.d("errorResponse.message", errorResponse.message)
                        Toast.makeText(PetWelfareApplication.context, errorResponse.msg, Toast.LENGTH_SHORT).show()
                        //continuation.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    Log.d("response","failure")
                    continuation.resumeWithException(t)
                }
            })
        }
    }

}