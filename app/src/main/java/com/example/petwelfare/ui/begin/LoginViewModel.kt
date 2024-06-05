package com.example.petwelfare.ui.begin


import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.logic.model.LoginResponse
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    fun login(mailbox: String, psd: String) {
        viewModelScope.launch {
            val loginResponse = PetWelfareNetwork.login(mailbox, psd)
            _loginResult.value = loginResponse
            MineDao.saveUserId(loginResponse.data.id)
            MineDao.saveMailbox(mailbox)
            MineDao.saveToken(loginResponse.data.access_token, loginResponse.data.refresh_token)
        }
    }

}