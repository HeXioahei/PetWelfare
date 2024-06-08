package com.example.petwelfare.ui.itemdetail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class StrayDetailViewModel : ViewModel() {

    var stray = Stray()
    var comments = mutableListOf<Comments>()

    private val _commentsInStray = MutableLiveData<GetCommentsResponse>()
    val commentsInStray : LiveData<GetCommentsResponse> = _commentsInStray

    private val _writeCommentsResponse = MutableLiveData<BaseResponse>()
    val writeCommentsResponse : LiveData<BaseResponse> = _writeCommentsResponse

    private val _collectResponse = MutableLiveData<BaseResponse>()
    val collectResponse : LiveData<BaseResponse> = _collectResponse

    private val _followResponse = MutableLiveData<BaseResponse>()
    val followResponse : LiveData<BaseResponse> = _followResponse

    fun getCommentsInStray(id: String) {
        viewModelScope.launch {
            _commentsInStray.value = PetWelfareNetwork.getCommentsInStray(id)
        }
    }

    fun writeComments(id: String, comment: String, time: String, lastCid: Int, level: Int) {
        viewModelScope.launch {
            _writeCommentsResponse.value = PetWelfareNetwork.writeCommentsInStray(id, comment, time, lastCid, level, Repository.Authorization)
        }
    }

    fun collect(strayId: String) {
        viewModelScope.launch {
            _collectResponse.value = PetWelfareNetwork.collectInStray(strayId, Repository.Authorization)
        }
    }

    fun follow(userId: String) {
        viewModelScope.launch {
            _followResponse.value = PetWelfareNetwork.follow(userId, Repository.Authorization)
        }
    }

}