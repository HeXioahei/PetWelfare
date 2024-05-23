package com.example.petwelfare.ui.main.mine.item.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.model.UserMostBrief

class ItemLikesViewModel : ViewModel() {

    private val listLiveData = MutableLiveData<UserMostBrief>()

    var likesArticle : MutableList<Article>  = mutableListOf(Article())

//    var likesArticleData = listLiveData.switchMap { data->
//        Repository.getMyStray(data.id, Repository.Authorization)
//    }

    fun setId (id2 : Long) {
        listLiveData.value?.id = id2
    }
}