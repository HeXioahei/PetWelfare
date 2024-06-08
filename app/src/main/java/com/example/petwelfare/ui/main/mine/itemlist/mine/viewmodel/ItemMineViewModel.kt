package com.example.petwelfare.ui.main.mine.itemlist.mine.viewmodel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.BlankFragment
import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.MyArticlesFragment
import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.MyLossFragment
import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.MyStrayFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemMineViewModel : ViewModel() {

    companion object {
        val _delMyArticle = MutableLiveData<BaseResponse>()
        val delMyArticle : LiveData<BaseResponse> = _delMyArticle

        val _delMyLoss = MutableLiveData<BaseResponse>()
        val delMyLoss : LiveData<BaseResponse> = _delMyLoss

        val _delMyStray = MutableLiveData<BaseResponse>()
        val delMyStray : LiveData<BaseResponse> = _delMyStray
    }

    var viewPagerList: List<Fragment> = listOf(
        MyArticlesFragment(Repository.myId),
        MyLossFragment(Repository.myId),
        MyStrayFragment(Repository.myId),
        BlankFragment()
    )

    var myArticlesList: MutableList<Article> = mutableListOf()
    var myLossList: MutableList<Loss> = mutableListOf()
    var myStrayList: MutableList<Stray> = mutableListOf()

    private val _myArticles = MutableLiveData<GetArticlesResponse>()
    val myArticles : LiveData<GetArticlesResponse> = _myArticles

    private val _myLoss = MutableLiveData<GetLossResponse>()
    val myLoss : LiveData<GetLossResponse> = _myLoss

    private val _myStray = MutableLiveData<GetStrayResponse>()
    val myStray : LiveData<GetStrayResponse>  = _myStray

    fun getMyArticles(id: Long) {
        viewModelScope.launch {
            Log.d("getMyArticles", "getMyArticles")
            _myArticles.value = PetWelfareNetwork.getMyArticles(id, Repository.Authorization)
            Log.d("getMyArticles", "${PetWelfareNetwork.getMyArticles(id, Repository.Authorization)}")
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

    fun delayAction(action: ()->Unit) {
        viewModelScope.launch {
            delay(5000)
            action.invoke()
        }
    }
}