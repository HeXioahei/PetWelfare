package com.example.petwelfare.ui.main.mine.edit

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class EditMyInfoViewModel : ViewModel() {

    private val _changeResponse = MutableLiveData<BaseResponse>()
    val changeResponse : LiveData<BaseResponse> = _changeResponse

    fun changeHead(headImage: MultipartBody.Part, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeHead(headImage, Authorization)
            val code = PetWelfareNetwork.changeHead(headImage, Authorization).code
            if (code == 200) {
                Log.d("changeHead", "success")
            } else {
                Log.d("changeHead", "failure")
            }
        }
    }

    fun changeUsername(username: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeUsername(username, Authorization)
            val code = PetWelfareNetwork.changeUsername(username, Authorization).code
            if (code == 200) {
                Log.d("changeUsername", "success")
            } else {
                Log.d("changeUsername", "failure")
            }
        }
    }

    fun changeAddress(address: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeAddress(address, Authorization)
            val code = PetWelfareNetwork.changeAddress(address, Authorization).code
            if (code == 200) {
                Log.d("changeAddress", "success")
            } else {
                Log.d("changeAddress", "failure")
            }
        }
    }

    fun changeTelephone(telephone: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeTelephone(telephone, Authorization)
            val code = PetWelfareNetwork.changeTelephone(telephone, Authorization).code
            if (code == 200) {
                Log.d("changeTelephone", "success")
            } else {
                Log.d("changeTelephone", "failure")
            }
        }
    }

    fun changePersonality(personality: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changePersonality(personality, Authorization)
            val code = PetWelfareNetwork.changePersonality(personality, Authorization).code
            if (code == 200) {
                Log.d("changePersonality", "success")
            } else {
                Log.d("changePersonality", "failure")
            }
        }
    }

    fun resetChangeResponse(code: Int, msg: String) {
        _changeResponse.value = BaseResponse(code, msg)
    }
}