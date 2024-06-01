package com.example.petwelfare.ui.item.loss

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class AddLossViewModel : ViewModel() {

    private val _sendLossResponse = MutableLiveData<BaseResponse>()
    val sendLossResponse : LiveData<BaseResponse> = _sendLossResponse

    fun sendLoss(
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
    ) {
        viewModelScope.launch {
            _sendLossResponse.value = PetWelfareNetwork.sendLoss(
                name, sex, type, address, contact,
                lostTime, sendTime, description, Authorization, photo_list
            )
        }
    }

}