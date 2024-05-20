package com.example.petwelfare.ui.begin


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {

    private val mm = MutableLiveData<AS>(AS("", ""))   //进行网络请求时需要传入的参数

    //    lateinit var myDetail: UserDetail
    var mmm: UserDetail = UserDetail()

    // 若上述参数比如id变化，则会自动调用下用这个方法，进行网络请求（已经封装好在Repository类中了，
    // 获取新的数据对象， 并将新获得的数据对象转化为当前的观察对象
    var mmmm = mm.switchMap { data ->
        Repository.login(data.mailbox, data.password)
    }


    fun login(mailbox: String, psd: String) : Int {
        var code = 0
        runBlocking {
            coroutineScope {
                launch {
                    Log.d("aaa", "aaa")
                    val response = PetWelfareNetwork.login(mailbox, psd)
                    code = response.code
                    Log.d("aaaa","aaaa")
                    if (code == 200) {
                        Log.d("login", "success")
                        Toast.makeText(PetWelfareApplication.context, "登录成功", Toast.LENGTH_SHORT).show()
                        val accessToken = response.data.access_token
                        Log.d("accessToken", accessToken)
                        val refreshToken = response.data.refresh_token
                        Repository.Authorization = accessToken
                        Repository.myId = response.data.id
                        Repository.refreshToken = refreshToken
                        MineDao.saveMailbox(mailbox)
                        MineDao.saveToken(accessToken, refreshToken)
                    } else {
                        Log.d("login", "failure")
                        Toast.makeText(PetWelfareApplication.context, "登陆失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        return code
    }

    fun setas(m: String, p: String) {
        mm.value?.mailbox = m
        mm.value?.password = p
    }
}

data class AS(var mailbox: String, var password: String)