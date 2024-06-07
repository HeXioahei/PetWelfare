package com.example.petwelfare.ui.main.mine.users

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetOrgsResponse
import com.example.petwelfare.logic.model.GetFollowsListResponse
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class FollowsViewModel : ViewModel() {

    var fragmentList = listOf(
        FollowsFragment(),
        CollectOrgsFragment()
    )

    var followsList : MutableList<UserBrief> = mutableListOf()
    var orsList : MutableList<Org> = mutableListOf()

    private val _followsListLiveData = MutableLiveData<GetFollowsListResponse>()
    val followsListLiveData : LiveData<GetFollowsListResponse> = _followsListLiveData

    private val _collectOrgsList = MutableLiveData<GetOrgsResponse>()
    val collectOrgsList : LiveData<GetOrgsResponse> = _collectOrgsList

    fun getFollows() {
        viewModelScope.launch {
            _followsListLiveData.value = PetWelfareNetwork.getFollows(Repository.Authorization)
        }
    }

    fun getCollectOrgs() {
        viewModelScope.launch {
            _collectOrgsList.value = PetWelfareNetwork.getCollectOrgs(Repository.Authorization)
        }
    }
}