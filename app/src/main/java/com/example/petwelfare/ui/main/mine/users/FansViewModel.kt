package com.example.petwelfare.ui.main.mine.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetUserBriefResponse
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.logic.model.UserMostBrief
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class FansViewModel : ViewModel() {

//    lateinit var fans : MutableList<UserBrief>
//
//    var fansData = Repository.getFans(Repository.Authorization)

    private val _fansList = MutableLiveData<GetUserBriefResponse>()
    val fansList : LiveData<GetUserBriefResponse> = _fansList

    fun getFans() {
        viewModelScope.launch {
            _fansList.value = PetWelfareNetwork.getFans(Repository.Authorization)
        }
    }

}