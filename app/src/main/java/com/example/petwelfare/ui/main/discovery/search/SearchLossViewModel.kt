package com.example.petwelfare.ui.main.discovery.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class SearchLossViewModel: ViewModel() {

    var searchLossList : MutableList<Loss> = mutableListOf()

    private val _searchLossResponse = MutableLiveData<GetLossResponse>()
    val searchLossResponse: LiveData<GetLossResponse> = _searchLossResponse

    fun searchLoss(keywords: String) {
        viewModelScope.launch {
            _searchLossResponse.value = PetWelfareNetwork.searchLoss(keywords, Repository.Authorization)
        }
    }

}