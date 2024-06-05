package com.example.petwelfare.logic.dao

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.petwelfare.PetWelfareApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// dao 即 data access object , 数据访问对象

object MineDao {

    val Context.dataStore1: DataStore<Preferences> by preferencesDataStore(name = "userId")
    val Context.dataStore2: DataStore<Preferences> by preferencesDataStore(name = "token")
    val Context.dataStore3: DataStore<Preferences> by preferencesDataStore(name = "user_mailbox")

    // 必须放在顶部

    // dataStore相关用法：
    // csdn博客：https://blog.csdn.net/zhaoyanjun6/article/details/127358235?ops_request_misc=&request_id=&biz_id=102&utm_term=DataStore&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-5-127358235.142^v100^pc_search_result_base1&spm=1018.2226.3001.4187
    // 官方文档：https://developer.android.google.cn/topic/libraries/architecture/datastore?hl=zh-cn#preferences-datastore



//    val keyUsername = stringPreferencesKey("username")
//    val keyAddress = stringPreferencesKey("address")
//    val keyTelephone = stringPreferencesKey("telephone")
//    val keyPersonality = stringPreferencesKey("personality")
//    val keyHeadImage = stringPreferencesKey("headImage")
    private val keyUserId = longPreferencesKey("userId")
    private val keyAccessToken = stringPreferencesKey("accessToken")
    private val keyRefreshToken = stringPreferencesKey("refreshToken")
    private val keyMailbox = stringPreferencesKey("mailbox")

//    suspend fun saveUser(user: UserDetail) {
//        PetWelfareApplication.context.dataStore1.edit {
//            it[keyUsername] = user.username
//        }
//    }

    suspend fun saveUserId(userId: Long) {
        PetWelfareApplication.context.dataStore1.edit {
            it[keyUserId] = userId
        }
    }

    suspend fun saveMailbox(mailbox: String) {
        PetWelfareApplication.context.dataStore3.edit {
            it[keyMailbox] = mailbox
        }
    }

    suspend fun saveToken(accessToken: String, refreshToken: String) {
        PetWelfareApplication.context.dataStore2.edit {
            it[keyAccessToken] = accessToken
            it[keyRefreshToken] = refreshToken
        }
    }

    fun getUserId() : Flow<Long> {
        return PetWelfareApplication.context.dataStore1.data.map {
            it[keyUserId] ?: 0
        }
    }

    fun getMailbox() : Flow<String> {
        return PetWelfareApplication.context.dataStore3.data.map {
            it[keyMailbox] ?: ""
        }
    }

    fun getAuthorization() : Flow<String> {
        Log.d("au1", "au1")
        return PetWelfareApplication.context.dataStore2.data.map {
            it[keyAccessToken] ?: ""
        }
    }

    fun getRefreshToken() : Flow<String> {
        Log.d("au2", "au2")
        return PetWelfareApplication.context.dataStore2.data.map {
            it[keyRefreshToken] ?: ""
        }
    }

//    Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsI             Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTcxN
//    Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhdCI6MTc   Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJmcmVzaCI6ZmFsc2UsImlhd


//    suspend fun getAuthorization() {
//        Log.d("au", "au")
//        PetWelfareApplication.context.dataStore2.data.map {
//            it[keyAccessToken]
//        }.collect {accessToken2->
//            Log.d("au1", "au1")
//            Repository.Authorization = accessToken2.toString()
//            Log.d("accessToken2.toString()", accessToken2.toString())
//            Log.d("Authorization", Repository.Authorization)
//            return@collect
//        }
//        PetWelfareApplication.context.dataStore2.data.map {
//            it[keyRefreshToken]
//        }.collect {refreshToken2->
//            Log.d("au2", "au2")
//            Repository.refreshToken = refreshToken2.toString()
//            Log.d("refreshToken2.toString()", refreshToken2.toString())
//            return@collect
//        }
//        Log.d("token", Repository.Authorization)
//    }
}