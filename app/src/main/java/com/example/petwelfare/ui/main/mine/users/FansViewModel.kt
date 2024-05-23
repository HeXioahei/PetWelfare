package com.example.petwelfare.ui.main.mine.users

import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.logic.model.UserMostBrief

class FansViewModel : ViewModel() {

    lateinit var fans : MutableList<UserBrief>

    var fansData = Repository.getFans(Repository.Authorization)

}