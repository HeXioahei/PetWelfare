package com.example.petwelfare.ui.main.head

import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.resource.bitmap.TransformationUtils
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemSquareViewModel : ViewModel() {

    var order = 1
    var articlesList: MutableList<Article> = mutableListOf()

    private val _articlesResponse = MutableLiveData<GetArticlesResponse>()
    val articlesResponse: LiveData<GetArticlesResponse> = _articlesResponse

    fun getArticles(order: Int) {
        viewModelScope.launch {
            Log.d("www", "www")
            _articlesResponse.value = PetWelfareNetwork.getArticles(order, Repository.Authorization)
            Log.d("_articlesResponse.value", "${_articlesResponse.value}")
            Log.d("articlesResponse", "${articlesResponse.value}")
            Log.d("eee", "eee")
        }
    }

    fun delayAction(action: ()->Unit) {
        viewModelScope.launch {
            delay(2000)
            action.invoke()
        }
    }


}