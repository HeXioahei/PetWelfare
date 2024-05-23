package com.example.petwelfare.ui.item.articles

import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddArticlesViewModel : ViewModel() {

    fun writeArticle(time: String, text: String, Authorization: String, photo_list: Map<String,RequestBody>) : Int {
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