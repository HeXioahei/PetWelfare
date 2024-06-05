package com.example.petwelfare.ui.main.add.stray

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

class AddStrayViewModel : ViewModel() {

    private val _addStraysResponse = MutableLiveData<BaseResponse>()
    val addStrayResponse : LiveData<BaseResponse> = _addStraysResponse

    fun sendStray(
        address: String,
        time: String,
        description: String,
        Authorization: String,
        photo_list: List<MultipartBody.Part>
    ) {
        viewModelScope.launch {
            _addStraysResponse.value = PetWelfareNetwork.sendStray(address, time, description, Authorization, photo_list)
        }
    }

}