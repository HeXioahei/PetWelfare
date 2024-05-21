package com.example.petwelfare.ui.begin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {

    private val _getVerificationResponseCode = MutableLiveData<Int>()
    val getVerificationResponseCode: LiveData<Int> = _getVerificationResponseCode

    private val _registerResponseCode = MutableLiveData<Int>()
    val registerResponseCode: LiveData<Int> = _registerResponseCode

    fun getVerification(mailbox2: String) {
        viewModelScope.launch {
            _getVerificationResponseCode.value = PetWelfareNetwork.getVerification("register", mailbox2).code
        }
    }

    fun register(mailbox2: String, psd: String, verification: String) {
        viewModelScope.launch {
            _registerResponseCode.value = PetWelfareNetwork.register(mailbox2, psd, verification).code
        }
    }
}
