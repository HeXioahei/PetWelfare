package com.example.petwelfare.ui.main.mine.pet

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class PetViewModel : ViewModel() {

    companion object {
        var petInfo : Pet = Pet()
    }


    fun changeHead(petId: Int, headImage: MultipartBody.Part, Authorization: String) {
        viewModelScope.launch {
            val code = PetWelfareNetwork.changePetHead(petId, headImage, Authorization).code
            if (code == 200) {
                Log.d("changeHead", "success")
            } else {
                Log.d("changeHead", "failure")
            }
        }
    }

    fun changeInfo(type: String, Authorization: String, newInfo: String, petId: Int) {
        var code : Int = 0
        viewModelScope.launch {
            when (type) {
                "name"-> {
                    code = PetWelfareNetwork.changePetName(petId, newInfo, Authorization).code
                }
                "type"-> {
                    code = PetWelfareNetwork.changePetType(petId, newInfo, Authorization).code
                }
                "sex"-> {
                    code = PetWelfareNetwork.changePetSex(petId, newInfo, Authorization).code
                }
                "birthday"-> {
                    code = PetWelfareNetwork.changePetBirthday(petId, newInfo, Authorization).code
                }
                "description"-> {
                    code = PetWelfareNetwork.changePetDescription(petId, newInfo, Authorization).code
                }
            }
        }
        if (code == 200) {
            Log.d("changePetInfo", "success")
        } else {
            Log.d("changePetInfo", "failure")
        }
    }

}