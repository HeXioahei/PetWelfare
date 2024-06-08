package com.example.petwelfare.ui.itemdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class LossDetailViewModel : ViewModel() {

    var loss = Loss()
    var comments = mutableListOf<Comments>()

    private val _commentsInLoss = MutableLiveData<GetCommentsResponse>()
    val commentsInLoss : LiveData<GetCommentsResponse> = _commentsInLoss

    private val _writeCommentsResponse = MutableLiveData<BaseResponse>()
    val writeCommentsResponse : LiveData<BaseResponse> = _writeCommentsResponse

    private val _collectResponse = MutableLiveData<BaseResponse>()
    val collectResponse : LiveData<BaseResponse> = _collectResponse

    private val _followResponse = MutableLiveData<BaseResponse>()
    val followResponse : LiveData<BaseResponse> = _followResponse

    fun getCommentsInLoss(id: String) {
        viewModelScope.launch {
            _commentsInLoss.value = PetWelfareNetwork.getCommentsInLoss(id)
        }
    }

    fun collect(lossId: String) {
        viewModelScope.launch {
            _collectResponse.value = PetWelfareNetwork.collectInLoss(lossId, Repository.Authorization)
        }
    }

    fun writeComments(id: String, comment: String, time: String, lastCid: Int, level: Int) {
        viewModelScope.launch {
            _writeCommentsResponse.value = PetWelfareNetwork.writeCommentsInLoss(id, comment, time, lastCid, level, Repository.Authorization)
        }
    }

    fun follow(userId: String) {
        viewModelScope.launch {
            _followResponse.value = PetWelfareNetwork.follow(userId, Repository.Authorization)
        }
    }

}