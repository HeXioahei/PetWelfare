package com.example.petwelfare.ui.main.mine.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.model.GetUserBriefListResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class FollowsViewModel : ViewModel() {

//    lateinit var follows : MutableList<UserBrief>
//
//    var followsData = Repository.getFollows(Repository.Authorization)

    private val _followsList = MutableLiveData<GetUserBriefListResponse>()
    val followsList : LiveData<GetUserBriefListResponse> = _followsList

    private val _collectOrgsList = MutableLiveData<GetOrgsResponse>()
    val collectOrgsList : LiveData<GetOrgsResponse> = _collectOrgsList

    fun getFans() {
        viewModelScope.launch {
            _followsList.value = PetWelfareNetwork.getFollows(Repository.Authorization)
        }
    }

    fun getCollectOrgs() {
        viewModelScope.launch {
            _collectOrgsList.value = PetWelfareNetwork.getCollectOrgs(Repository.Authorization)
        }
    }
}