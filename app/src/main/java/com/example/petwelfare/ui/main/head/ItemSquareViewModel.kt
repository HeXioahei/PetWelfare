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
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ItemSquareViewModel : ViewModel() {

    private val _articlesResponse = MutableLiveData<GetArticlesResponse>()
    val articlesResponse: LiveData<GetArticlesResponse> = _articlesResponse

//    private val articlesOrder = MutableLiveData(0)
//    val articlesResponse = articlesOrder.switchMap { data->
//        Log.d("articlesOrder.value0", "${articlesOrder.value}")
//        Log.d("toRequest22222", "going")
//        Repository.getArticles(data, Repository.Authorization)
////        PetWelfareNetwork.getArticles(data, Repository.Authorization)
//    }

//    fun setOrder(order: Int) {
//        Log.d("setOrder", "$order")
//        Log.d("articlesOrder.value1", "${articlesOrder.value}")
//        articlesOrder.value = order
//        Log.d("articlesOrder.value2", "${articlesOrder.value}")
//    }

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
            delay(5000)
            action.invoke()
        }
    }


}