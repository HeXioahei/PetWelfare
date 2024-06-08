package com.example.petwelfare.ui.main.discovery.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class SearchRescueViewModel : ViewModel() {

    var searchOrgsList : MutableList<Org> = mutableListOf()

    private val _searchOrgsResponse = MutableLiveData<GetOrgsResponse>()
    val searchOrgsResponse: LiveData<GetOrgsResponse> = _searchOrgsResponse

    fun searchOrgs(keywords: String) {
        viewModelScope.launch {
            _searchOrgsResponse.value = PetWelfareNetwork.searchOrgs(keywords, Repository.Authorization)
        }
    }

}