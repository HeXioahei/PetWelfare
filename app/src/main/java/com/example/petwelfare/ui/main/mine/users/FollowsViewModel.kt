package com.example.petwelfare.ui.main.mine.users

import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.UserBrief

class FollowsViewModel : ViewModel() {

    lateinit var follows : MutableList<UserBrief>

    var followsData = Repository.getFollows(Repository.Authorization)
}