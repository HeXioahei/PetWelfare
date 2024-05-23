package com.example.petwelfare.ui.main.mine.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Stray

class ListViewModel: ViewModel() {

    // 以下三个列表是用来缓存列表的
    lateinit var myArticles: MutableList<Article>
    lateinit var myLoss: MutableList<Loss>
    lateinit var myStray: MutableList<Stray>

    lateinit var myArticlesData : LiveData<Result<MutableList<Article>>>
    lateinit var myLossData : LiveData<Result<MutableList<Loss>>>
    lateinit var myStrayData : LiveData<Result<MutableList<Stray>>>
    fun refreshMyArticles(id: Long, Authorization: String) {
        myArticlesData = Repository.getMyArticles(id, Authorization)
    }

    fun refreshMyLoss(id: Long, Authorization: String) {
        myLossData = Repository.getMyLoss(id, Authorization)
    }

    fun refreshMyStray(id: Long, Authorization: String) {
        myStrayData = Repository.getMyStray(id, Authorization)
    }
}