package com.example.petwelfare.ui.main.mine.pet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetPetsInfoResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PetListViewModel : ViewModel() {

    private val _myPetList = MutableLiveData<GetPetsInfoResponse>()
    val myPetList : LiveData<GetPetsInfoResponse> = _myPetList

    fun getMyPetList() {
        viewModelScope.launch {
            _myPetList.value = PetWelfareNetwork.getPetsInfo(Repository.myId)
        }
    }

    fun delayAction(action: ()->Unit) {
        viewModelScope.launch {
            delay(5000)
            action.invoke()
        }
    }
}