package com.example.petwelfare.ui.mine.users

import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.UserBrief

class FollowsViewModel : ViewModel() {

    lateinit var follows : MutableList<UserBrief>

    var followsData = Repository.getFollows(Repository.Authorization)
}