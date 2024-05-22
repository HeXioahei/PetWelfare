package com.example.petwelfare.ui.head

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemSquareViewModel : ViewModel() {

    private val _articlesResponse = MutableLiveData<GetArticlesResponse>()
    val articlesResponse: LiveData<GetArticlesResponse> = _articlesResponse

    fun getArticles() {
        viewModelScope.launch {
            _articlesResponse.value = PetWelfareNetwork.getArticles(0, Repository.Authorization)
        }
    }

    fun delayAction(action: ()->Unit) {
        viewModelScope.launch {
            delay(2000)
            action.invoke()
        }
    }
}