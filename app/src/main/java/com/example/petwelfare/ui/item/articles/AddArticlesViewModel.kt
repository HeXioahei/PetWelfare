package com.example.petwelfare.ui.item.articles

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

    private val _addArticlesResponse = MutableLiveData<BaseResponse>()
    val addArticlesResponse : LiveData<BaseResponse> = _addArticlesResponse

    fun writeArticle(time: String, text: String, Authorization: String, photo_list: Map<String, RequestBody>) {
        viewModelScope.launch {
            _addArticlesResponse.value = PetWelfareNetwork.writeArticle(time, text, Authorization, photo_list)
        }
    }

    fun resetChangeResponse(code: Int, msg: String) {
        _addArticlesResponse.value = BaseResponse(code, msg)
    }

//    fun writeArticle(time: String, text: String, Authorization: String, photo_list: MultipartBody.Part) : Int {
//        var code = 0
//        runBlocking {
//            coroutineScope {
//                launch {
//                    code = PetWelfareNetwork.writeArticle(time, text, Authorization, photo_list).code
//                }
//            }
//        }
//        return code
//    }

}