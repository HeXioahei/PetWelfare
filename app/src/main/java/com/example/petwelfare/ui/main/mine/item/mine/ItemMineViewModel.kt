package com.example.petwelfare.ui.main.mine.item.mine

import android.util.Log
import androidx.fragment.app.Fragment
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
import com.example.petwelfare.ui.BlankFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemMineViewModel : ViewModel() {

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