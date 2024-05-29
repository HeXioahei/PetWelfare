package com.example.petwelfare.ui.main.mine.item.collection

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class ItemCollectionViewModel : ViewModel() {

    var viewPagerList: List<Fragment> = listOf(
        CollectArticlesFragment(),
        CollectLossFragment(),
        CollectStrayFragment()
    )

    var collectArticlesList: MutableList<Article> = mutableListOf(Article(), Article(),Article(),Article())
    var collectLossList: MutableList<Loss> = mutableListOf(Loss(), Loss(), Loss(), Loss())
    var collectStrayList: MutableList<Stray> = mutableListOf(Stray(), Stray(), Stray(), Stray())

    private val _collectArticles = MutableLiveData<GetArticlesResponse>()
    val collectArticles : LiveData<GetArticlesResponse> = _collectArticles

    private val _collectLoss = MutableLiveData<GetLossResponse>()
    val collectLoss : LiveData<GetLossResponse> = _collectLoss

    private val _collectStray = MutableLiveData<GetStrayResponse>()
    val collectStray : LiveData<GetStrayResponse> = _collectStray

    fun getCollectArticles() {
        viewModelScope.launch {
            _collectArticles.value = PetWelfareNetwork.getCollectArticles(Repository.Authorization)
        }
    }

    fun getCollectLoss() {
        viewModelScope.launch {
            _collectLoss.value = PetWelfareNetwork.getCollectLoss(Repository.Authorization)
        }
    }

    fun getCollectStray() {
        viewModelScope.launch {
            _collectStray.value = PetWelfareNetwork.getCollectStray(Repository.Authorization)
        }
    }

}