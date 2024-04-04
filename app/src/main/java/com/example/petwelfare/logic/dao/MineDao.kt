package com.example.petwelfare.logic.dao

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.Token
import com.example.petwelfare.logic.model.UserDetail

// dao 即 data access object , 数据访问对象

object MineDao {

    val Context.dataStore1: DataStore<Preferences> by preferencesDataStore(name = "user_info")
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
    val keyAccessToken = stringPreferencesKey("accessToken")
    val keyRefreshToken = stringPreferencesKey("refreshToken")
    val keyMailbox = stringPreferencesKey("mailbox")

//    suspend fun saveUser(user: UserDetail) {
//        PetWelfareApplication.context.dataStore1.edit {
//            it[keyUsername] = user.username
//        }
//    }

    suspend fun saveMailbox(maibox: String) {
        PetWelfareApplication.context.dataStore3.edit {
            it[keyMailbox] = maibox
        }
    }

    suspend fun saveToken(token: Token) {
        PetWelfareApplication.context.dataStore2.edit {
            it[keyAccessToken] = token.access_token
            it[keyRefreshToken] = token.refresh_token
        }
    }
}