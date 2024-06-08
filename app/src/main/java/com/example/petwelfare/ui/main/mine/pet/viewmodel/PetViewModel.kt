package com.example.petwelfare.ui.main.mine.pet.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class PetViewModel : ViewModel() {

    companion object {
        var petInfo : Pet = Pet()
        var petTempInfo : Pet = Pet()
        var photosUri = mutableListOf<Uri>()
        var tempPhotosUri = mutableListOf<Uri>()

        private val _changeResponse = MutableLiveData<BaseResponse>()
        val changeResponse : LiveData<BaseResponse> = _changeResponse
    }



    fun changeHead(petId: Int, headImage: MultipartBody.Part, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changePetHead(petId, headImage, Authorization)
        }
    }

    fun changeInfo(type: String, Authorization: String, newInfo: String, petId: Int) {
        viewModelScope.launch {
            when (type) {
                "name"-> {
                    _changeResponse.value = PetWelfareNetwork.changePetName(petId, newInfo, Authorization)
                }
                "type"-> {
                    _changeResponse.value = PetWelfareNetwork.changePetType(petId, newInfo, Authorization)
                }
                "sex"-> {
                    _changeResponse.value = PetWelfareNetwork.changePetSex(petId, newInfo.toInt(), Authorization)
                }
                "birthday"-> {
                    _changeResponse.value = PetWelfareNetwork.changePetBirthday(petId, newInfo, Authorization)
                }
                "description"-> {
                    _changeResponse.value = PetWelfareNetwork.changePetDescription(petId, newInfo, Authorization)
                }
            }
        }

    }

    fun addPicture(petId: Int, photos: List<MultipartBody.Part>) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.addPicture(petId, photos, Repository.Authorization)
        }
    }

}