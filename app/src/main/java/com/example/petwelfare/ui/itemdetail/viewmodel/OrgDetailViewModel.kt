package com.example.petwelfare.ui.itemdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class OrgDetailViewModel : ViewModel() {

    var org = Org()

    private val _followOrgResponse = MutableLiveData<BaseResponse>()
    val followOrgResponse : LiveData<BaseResponse> = _followOrgResponse

    fun followOrg(id: String) {
        viewModelScope.launch {
            _followOrgResponse.value = PetWelfareNetwork.followOrg(id, Repository.Authorization)
        }
    }

}