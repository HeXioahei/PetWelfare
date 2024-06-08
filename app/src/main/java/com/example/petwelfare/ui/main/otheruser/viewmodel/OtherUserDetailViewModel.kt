package com.example.petwelfare.ui.main.otheruser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetPetsInfoResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.main.mine.MineViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OtherUserDetailViewModel : ViewModel() {

    var otherUserDetail = UserDetail()

    private val _otherUserDetailLiveData = MutableLiveData<UserDetail>()
    val otherUserDetailLiveData: LiveData<UserDetail> = _otherUserDetailLiveData

    private val _followResponse = MutableLiveData<BaseResponse>()
    val followResponse : LiveData<BaseResponse> = _followResponse

    fun getUserDetail(userId: Long) {
        viewModelScope.launch {
            _otherUserDetailLiveData.value =
                PetWelfareNetwork.getUserInfo(userId, Repository.Authorization).data.author
            Log.d("userDetail", PetWelfareNetwork.getUserInfo(userId, Repository.Authorization).data.author.toString())
        }
    }

    fun delayAction(action: () -> Unit) {
        viewModelScope.launch {
            delay(5000)
            action.invoke()
        }
    }

    fun follow(userId: String) {
        viewModelScope.launch {
            _followResponse.value = PetWelfareNetwork.follow(userId, Repository.Authorization)
        }
    }
}