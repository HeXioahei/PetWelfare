package com.example.petwelfare.ui.item.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class ArticleDetailViewModel : ViewModel() {

    private val _commentsInArticle = MutableLiveData<GetCommentsResponse>()
    val commentsInArticle : LiveData<GetCommentsResponse> = _commentsInArticle

    private val _writeCommentsResponse = MutableLiveData<BaseResponse>()
    val writeCommentsResponse : LiveData<BaseResponse> = _writeCommentsResponse

    fun getCommentsInArticle(id: String) {
        viewModelScope.launch {
            _commentsInArticle.value = PetWelfareNetwork.getCommentsInArticles(id)
        }
    }

    fun writeComments(id: String, comment: String, time: String, lastCid: Int, level: Int) {
        viewModelScope.launch {
            _writeCommentsResponse.value = PetWelfareNetwork.writeCommentsInArticles(id, comment, time, lastCid, level)
        }
    }
}