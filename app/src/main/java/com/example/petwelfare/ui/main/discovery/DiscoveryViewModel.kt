package com.example.petwelfare.ui.main.discovery

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetAddressResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiscoveryViewModel : ViewModel() {

    val viewPagerList: List<Fragment> = listOf(
        ItemLossFragment(),
        ItemStrayFragment(),
        ItemRescueFragment(),
        ItemFosterFragment(),
        ItemAdoptionFragment()
    )


    var lossList: MutableList<Loss> = mutableListOf()

    var strayList: MutableList<Stray> = mutableListOf()

    var orgsList: MutableList<Org> = mutableListOf()

    companion object {
        var address = "福州市"
        var addressDetail = "福建省福州市鼓楼区五凤街道软件园"

        private val _addressDefaultLiveData = MutableLiveData<GetAddressResponse>()
        val addressDefaultLiveData : LiveData<GetAddressResponse> = _addressDefaultLiveData

        private val _addressLiveData = MutableLiveData<String>()
        val addressLiveData : LiveData<String> = _addressLiveData

        private val _addressDetailLiveData = MutableLiveData<String>()
        val addressDetailLiveData : LiveData<String> = _addressDetailLiveData

        fun changeAddress(address2: String) {
            _addressLiveData.value = address2
        }

        fun changeAddressDetail(address2: String) {
            _addressDetailLiveData.value = address2
        }

    }

    private val _lossResponse = MutableLiveData<GetLossResponse>()
    val lossResponse: LiveData<GetLossResponse> = _lossResponse

    private val _strayResponse = MutableLiveData<GetStrayResponse>()
    val strayResponse: LiveData<GetStrayResponse> = _strayResponse

    private val _orgsResponse = MutableLiveData<GetOrgsResponse>()
    val orgsResponse: LiveData<GetOrgsResponse> = _orgsResponse

    fun getAddressDefault() {
        viewModelScope.launch {
            _addressDefaultLiveData.value = PetWelfareNetwork.getAddressDefault()
        }
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