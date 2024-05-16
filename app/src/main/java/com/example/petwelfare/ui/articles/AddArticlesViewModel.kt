package com.example.petwelfare.ui.articles

import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class AddArticlesViewModel : ViewModel() {

    fun writeArticle(time: String, text: String, Authorization: String, photo_list: List<MultipartBody.Part>) : Int {
        var code = 0
        runBlocking {
            coroutineScope {
                launch {
                    code = PetWelfareNetwork.writeArticle(time, text, Authorization, photo_list).code
                }
            }
        }
        return code
    }

}