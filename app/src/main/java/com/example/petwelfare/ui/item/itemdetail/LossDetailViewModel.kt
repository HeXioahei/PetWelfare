package com.example.petwelfare.ui.item.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class LossDetailViewModel : ViewModel() {

    private val _commentsInLoss = MutableLiveData<GetCommentsResponse>()
    val commentsInLoss : LiveData<GetCommentsResponse> = _commentsInLoss

    private val _writeCommentsResponse = MutableLiveData<BaseResponse>()
    val writeCommentsResponse : LiveData<BaseResponse> = _writeCommentsResponse

    fun getCommentsInLoss(id: String) {
        viewModelScope.launch {
            _commentsInLoss.value = PetWelfareNetwork.getCommentsInLoss(id)
        }
    }

    fun writeComments(id: String, comment: String, time: String, lastCid: Int, level: Int) {
        viewModelScope.launch {
            _writeCommentsResponse.value = PetWelfareNetwork.writeCommentsInLoss(id, comment, time, lastCid, level)
        }
    }

}