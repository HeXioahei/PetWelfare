package com.example.petwelfare.ui.main.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiscoveryViewModel : ViewModel() {

    private val _address = MutableLiveData<String>()
    val address : LiveData<String> = _address

    private val _lossResponse = MutableLiveData<GetLossResponse>()
    val lossResponse: LiveData<GetLossResponse> = _lossResponse

    private val _strayResponse = MutableLiveData<GetStrayResponse>()
    val strayResponse: LiveData<GetStrayResponse> = _strayResponse

    private val _orgsResponse = MutableLiveData<GetOrgsResponse>()
    val orgsResponse: LiveData<GetOrgsResponse> = _orgsResponse

    fun changeAddress(address2: String) {
        _address.value = address2
    }

    fun getLoss(address: String) {
        viewModelScope.launch {
            _lossResponse.value = PetWelfareNetwork.getLoss(address, Repository.Authorization)
        }
    }

    fun getStray(address: String) {
        viewModelScope.launch {
            _strayResponse.value = PetWelfareNetwork.getStray(address, Repository.Authorization)
        }
    }

    fun getOrgs() {
        viewModelScope.launch {
            _orgsResponse.value = PetWelfareNetwork.getOrgs(Repository.Authorization)
        }
    }

    fun delayAction(action: ()->Unit) {
        viewModelScope.launch {
            delay(2000)
            action.invoke()
        }
    }

}