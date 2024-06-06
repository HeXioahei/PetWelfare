package com.example.petwelfare.ui.main.discovery.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetStraySearchResponse
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class SearchStrayViewModel: ViewModel() {

    var searchStrayList : MutableList<Stray> = mutableListOf()

    private val _searchStrayResponse = MutableLiveData<GetStraySearchResponse>()
    val searchStrayResponse: LiveData<GetStraySearchResponse> = _searchStrayResponse

    fun searchStray(keywords: String) {
        viewModelScope.launch {
            _searchStrayResponse.value = PetWelfareNetwork.searchStray(keywords, Repository.Authorization)
        }
    }

}