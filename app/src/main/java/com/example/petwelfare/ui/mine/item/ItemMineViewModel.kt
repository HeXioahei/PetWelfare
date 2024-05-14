package com.example.petwelfare.ui.mine.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.model.UserMostBrief

class ItemMineViewModel : ViewModel() {

    private val listLiveData = MutableLiveData<UserMostBrief>()

//    lateinit var myArticles : MutableList<Article>
    var myArticles : MutableList<Article> = mutableListOf(Article(), Article(), Article(), Article(), Article())
//    lateinit var myLoss : MutableList<Loss>
    var myLoss : MutableList<Loss> = mutableListOf(Loss())
//    lateinit var myStray : MutableList<Stray>
    var myStray : MutableList<Stray> = mutableListOf(Stray())

    var myArticlesData = listLiveData.switchMap { data->
        Repository.getMyArticles(data.id, Repository.Authorization)
    }

    var myLossData = listLiveData.switchMap { data->
        Repository.getMyLoss(data.id, Repository.Authorization)
    }

    var myStrayData = listLiveData.switchMap { data->
        Repository.getMyStray(data.id, Repository.Authorization)
    }

    fun setId (id2 : Long) {
        listLiveData.value?.id = id2
    }
}