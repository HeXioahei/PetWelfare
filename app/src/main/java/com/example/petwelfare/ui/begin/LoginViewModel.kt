package com.example.petwelfare.ui.begin


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {

    fun login(mailbox: String, psd: String) : Int {
        var code = 0
        runBlocking {
            coroutineScope {
                launch {
                    val response = PetWelfareNetwork.login(mailbox, psd)
                    code = response.code
                    if (code == 200) {
                        Log.d("login", "success")
                        Toast.makeText(PetWelfareApplication.context, "登录成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("login", "failure")
                        Toast.makeText(PetWelfareApplication.context, "登陆失败", Toast.LENGTH_SHORT).show()
                    }
                    val accessToken = response.data.accessToken
                    val refreshToken = response.data.refreshToken
                    Repository.Authorization = accessToken
                    Repository.userId = response.data.id
                    Repository.refreshToken = refreshToken
                    MineDao.saveMailbox(mailbox)
                    MineDao.saveToken(accessToken, refreshToken)
                }
            }
        }
        return code
    }



}