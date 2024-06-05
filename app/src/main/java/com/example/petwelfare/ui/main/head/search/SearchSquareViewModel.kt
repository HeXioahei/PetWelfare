package com.example.petwelfare.ui.main.head.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchSquareViewModel : ViewModel() {

    var searchArticlesList : MutableList<Article> = mutableListOf()

    private val _searchArticlesResponse = MutableLiveData<GetArticlesResponse>()
    val searchArticlesResponse: LiveData<GetArticlesResponse> = _searchArticlesResponse

    fun searchArticles(keywords: String) {
        viewModelScope.launch {
            _searchArticlesResponse.value = PetWelfareNetwork.searchArticles(keywords, Repository.Authorization)
        }
    }
}