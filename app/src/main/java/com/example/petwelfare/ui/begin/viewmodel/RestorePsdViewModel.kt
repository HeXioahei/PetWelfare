package com.example.petwelfare.ui.begin.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RestorePsdViewModel : ViewModel() {

    private val _getVerificationResponseCode = MutableLiveData<Int>()
    val getVerificationResponseCode: LiveData<Int> = _getVerificationResponseCode

    private val _resetPsdResponse = MutableLiveData<BaseResponse>()
    val resetPsdResponse: LiveData<BaseResponse> = _resetPsdResponse

    fun getVerification(mailbox2: String) {
        viewModelScope.launch {
            _getVerificationResponseCode.value = PetWelfareNetwork.getVerification("pwd", mailbox2).code
        }
    }

    fun resetPsd(mailbox2: String, psd: String, verification: String) {
        viewModelScope.launch {
            Log.d("verification", verification)
            _resetPsdResponse.value = PetWelfareNetwork.resetPassword(mailbox2, psd, verification)
        }
    }
}