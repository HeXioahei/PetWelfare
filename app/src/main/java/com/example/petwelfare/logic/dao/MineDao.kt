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

object MineDao {

    val Context.dataStore1: DataStore<Preferences> by preferencesDataStore(name = "userId")
    val Context.dataStore2: DataStore<Preferences> by preferencesDataStore(name = "token")
    val Context.dataStore3: DataStore<Preferences> by preferencesDataStore(name = "user_mailbox")

    private val keyUserId = longPreferencesKey("userId")
    private val keyAccessToken = stringPreferencesKey("accessToken")
    private val keyRefreshToken = stringPreferencesKey("refreshToken")
    private val keyMailbox = stringPreferencesKey("mailbox")

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

}