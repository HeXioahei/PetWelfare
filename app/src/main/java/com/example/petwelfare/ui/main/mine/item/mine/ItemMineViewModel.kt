package com.example.petwelfare.ui.main.mine.item.mine

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.model.UserMostBrief
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class ItemMineViewModel : ViewModel() {

    //private val listLiveData = MutableLiveData<UserMostBrief>()

////    lateinit var myArticles : MutableList<Article>
//    var myArticles : MutableList<Article> = mutableListOf(Article(), Article(), Article(), Article(), Article())
////    lateinit var myLoss : MutableList<Loss>
//    var myLoss : MutableList<Loss> = mutableListOf(Loss())
////    lateinit var myStray : MutableList<Stray>
//    var myStray : MutableList<Stray> = mutableListOf(Stray())
//
//    var myArticlesData = listLiveData.switchMap { data->
//        Repository.getMyArticles(data.id, Repository.Authorization)
//    }
//
//    var myLossData = listLiveData.switchMap { data->
//        Repository.getMyLoss(data.id, Repository.Authorization)
//    }
//
//    var myStrayData = listLiveData.switchMap { data->
//        Repository.getMyStray(data.id, Repository.Authorization)
//    }
//
//    fun setId (id2 : Long) {
//        listLiveData.value?.id = id2
//    }

    private val _myArticles = MutableLiveData<GetArticlesResponse>()
    val myArticles : LiveData<GetArticlesResponse> = _myArticles

    private val _myLoss = MutableLiveData<GetLossResponse>()
    val myLoss : LiveData<GetLossResponse> = _myLoss

    private val _myStray = MutableLiveData<GetStrayResponse>()
    val myStray : LiveData<GetStrayResponse>  = _myStray

    fun getMyArticles(id: Long) {
        viewModelScope.launch {
            _myArticles.value = PetWelfareNetwork.getMyArticles(id, Repository.Authorization)
        }
    }

    fun getMyLoss(id: Long) {
        viewModelScope.launch {
            _myLoss.value = PetWelfareNetwork.getMyLoss(id, Repository.Authorization)
        }
    }

    fun getMyStray(id: Long) {
        viewModelScope.launch {
            _myStray.value = PetWelfareNetwork.getMyStray(id, Repository.Authorization)
        }
    }

}