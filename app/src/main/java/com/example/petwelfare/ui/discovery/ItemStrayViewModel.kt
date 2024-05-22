package com.example.petwelfare.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemStrayViewModel: ViewModel() {

    private val _strayResponse = MutableLiveData<GetStrayResponse>()
    val lossResponse: LiveData<GetStrayResponse> = _strayResponse

    fun getStray(address: String) {
        viewModelScope.launch {
            _strayResponse.value = PetWelfareNetwork.getStray(address, Repository.Authorization)
        }
    }

    fun delayAction(action: ()->Unit) {
        viewModelScope.launch {
            delay(2000)
            action.invoke()
        }
    }

}