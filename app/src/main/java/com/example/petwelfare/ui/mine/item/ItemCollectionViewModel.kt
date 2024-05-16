package com.example.petwelfare.ui.mine.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.model.UserMostBrief

class ItemCollectionViewModel : ViewModel() {

    var collectArticles : MutableList<Article> = mutableListOf(Article())
    var collectLoss : MutableList<Loss> = mutableListOf(Loss())
    var collectStray : MutableList<Stray> = mutableListOf(Stray())
    var collectOrg : MutableList<Org> = mutableListOf(Org())

    var collectArticlesData = Repository.getCollectArticles(Repository.Authorization)

    var collectLossData = Repository.getCollectLoss(Repository.Authorization)

    var collectStrayData = Repository.getCollectStary(Repository.Authorization)

    var collectOrgData = Repository.getCollectOrgs(Repository.Authorization)


}