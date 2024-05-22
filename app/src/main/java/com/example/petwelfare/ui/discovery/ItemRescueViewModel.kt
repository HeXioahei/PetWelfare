package com.example.petwelfare.ui.discovery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemRescueViewModel : ViewModel() {

    private val _orgsResponse = MutableLiveData<GetOrgsResponse>()
    val lossResponse: LiveData<GetOrgsResponse> = _orgsResponse

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