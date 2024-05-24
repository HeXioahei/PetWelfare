package com.example.petwelfare.ui.item.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class StrayDetailViewModel : ViewModel() {

    private val _commentsInStray = MutableLiveData<GetCommentsResponse>()
    val commentsInStray : LiveData<GetCommentsResponse> = _commentsInStray

    private val _writeCommentsResponse = MutableLiveData<BaseResponse>()
    val writeCommentsResponse : LiveData<BaseResponse> = _writeCommentsResponse

    fun getCommentsInStray(id: String) {
        viewModelScope.launch {
            _commentsInStray.value = PetWelfareNetwork.getCommentsInStray(id)
        }
    }

    fun writeComments(id: String, comment: String, time: String, lastCid: Int, level: Int) {
        viewModelScope.launch {
            _writeCommentsResponse.value = PetWelfareNetwork.writeCommentsInStray(id, comment, time, lastCid, level)
        }
    }

}