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

    lateinit var collectArticles : MutableList<Article>
    lateinit var collectLoss : MutableList<Loss>
    lateinit var collectStray : MutableList<Stray>
    lateinit var collectOrg : MutableList<Org>

    var collectArticlesData = Repository.getCollectArticles(Repository.Authorization)

    var collectLossData = Repository.getCollectLoss(Repository.Authorization)

    var collectStrayData = Repository.getCollectStary(Repository.Authorization)

    var collectOrgData = Repository.getCollectOrgs(Repository.Authorization)


}