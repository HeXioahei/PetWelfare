package com.example.petwelfare.ui.main.mine.users.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetFansListResponse
import com.example.petwelfare.logic.model.GetFollowsListResponse
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class FansViewModel : ViewModel() {

    var fansList : MutableList<UserBrief> = mutableListOf()
    var friendsList : MutableList<UserBrief> = mutableListOf()

    private val _fansListLiveData = MutableLiveData<GetFansListResponse>()
    val fansListLiveData : LiveData<GetFansListResponse> = _fansListLiveData

    fun getFans() {
        viewModelScope.launch {
            _fansListLiveData.value = PetWelfareNetwork.getFans(Repository.Authorization)
        }
    }

}