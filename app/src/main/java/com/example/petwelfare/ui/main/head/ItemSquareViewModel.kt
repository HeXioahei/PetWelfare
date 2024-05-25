package com.example.petwelfare.ui.main.head

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
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
            Log.d("www", "www")
            _articlesResponse.value = PetWelfareNetwork.getArticles(1, Repository.Authorization)
            Log.d("eee", "eee")
        }
    }

    fun delayAction(action: ()->Unit) {
        viewModelScope.launch {
            delay(5000)
            action.invoke()
        }
    }
}