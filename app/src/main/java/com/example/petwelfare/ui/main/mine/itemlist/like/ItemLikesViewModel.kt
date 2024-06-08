package com.example.petwelfare.ui.main.mine.itemlist.like

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class ItemLikesViewModel : ViewModel() {

    var likeArticlesList: MutableList<Article> = mutableListOf()

    private val _likeArticles = MutableLiveData<GetArticlesResponse>()
    val likeArticles : LiveData<GetArticlesResponse> = _likeArticles

    fun getCollectArticles() {
        viewModelScope.launch {
            _likeArticles.value = PetWelfareNetwork.getCollectArticles(Repository.Authorization)
        }
    }
}