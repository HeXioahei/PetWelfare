package com.example.petwelfare.ui.main.add.articles

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddArticlesViewModel : ViewModel() {

    var photosList = mutableListOf<@JvmSuppressWildcards Uri>()

    private val _addArticlesResponse = MutableLiveData<BaseResponse>()
    val addArticlesResponse : LiveData<BaseResponse> = _addArticlesResponse

    fun writeArticle(time: String, text: String, Authorization: String, photo_list: List<MultipartBody.Part>) {
        Log.d("photo_list", photo_list.toString())
        viewModelScope.launch {
            _addArticlesResponse.value = PetWelfareNetwork.writeArticle(time, text, Authorization, photo_list)
        }
    }

}