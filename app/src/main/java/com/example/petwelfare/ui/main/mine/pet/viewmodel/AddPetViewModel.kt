package com.example.petwelfare.ui.main.mine.pet.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class AddPetViewModel: ViewModel() {

    private val _addPetsResponse = MutableLiveData<BaseResponse>()
    val addPetsResponse : LiveData<BaseResponse> = _addPetsResponse

    fun addPets(
        name: String,
        sex: String,
        type: String,
        birthday: String,
        description: String,
        Authorization: String,
        headImage: MultipartBody.Part
    ) {
        viewModelScope.launch {
            _addPetsResponse.value = PetWelfareNetwork.addPets(
                name, sex, type, birthday, description,
                Authorization, headImage
            )
        }
    }

}