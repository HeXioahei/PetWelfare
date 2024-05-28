package com.example.petwelfare.ui.item.itemdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.GetCommentsResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class ArticleDetailViewModel : ViewModel() {

    var article = Article()
    var comments : MutableList<Comments> = mutableListOf()

    private val _commentsInArticle = MutableLiveData<GetCommentsResponse>()
    val commentsInArticle : LiveData<GetCommentsResponse> = _commentsInArticle

    private val _writeCommentsResponse = MutableLiveData<BaseResponse>()
    val writeCommentsResponse : LiveData<BaseResponse> = _writeCommentsResponse

    private val _likeResponse = MutableLiveData<BaseResponse>()
    val likeResponse : LiveData<BaseResponse> = _likeResponse

    private val _collectResponse = MutableLiveData<BaseResponse>()
    val collectResponse : LiveData<BaseResponse> = _collectResponse

    private val _followResponse = MutableLiveData<BaseResponse>()
    val followResponse : LiveData<BaseResponse> = _followResponse

    fun getCommentsInArticle(id: String) {
        viewModelScope.launch {
            _commentsInArticle.value = PetWelfareNetwork.getCommentsInArticles(id)
        }
    }

    fun like(articleId: String) {
        viewModelScope.launch {
            _likeResponse.value = PetWelfareNetwork.likeInArticles(articleId, Repository.Authorization)
        }
    }

    fun collect(articleId: String) {
        viewModelScope.launch {
            _collectResponse.value = PetWelfareNetwork.collectInArticles(articleId, Repository.Authorization)
        }
    }

    fun writeComments(id: String, comment: String, time: String, lastCid: Int, level: Int) {
        viewModelScope.launch {
            _writeCommentsResponse.value = PetWelfareNetwork.writeCommentsInArticles(id, comment, time, lastCid, level)
        }
    }

    fun follow(userId: String) {
        viewModelScope.launch {
            _followResponse.value = PetWelfareNetwork.follow(userId, Repository.Authorization)
        }
    }
}