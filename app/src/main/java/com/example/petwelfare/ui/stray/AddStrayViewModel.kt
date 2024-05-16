package com.example.petwelfare.ui.stray

import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class AddStrayViewModel : ViewModel() {

    fun sendStray(
        address: String,
        time: String,
        description: String,
        Authorization: String,
        photo_list: List<MultipartBody.Part>
    ) : Int {
        var code = 0
        runBlocking {
            coroutineScope {
                launch {
                    code = PetWelfareNetwork.sendStray(address, time, description, Authorization, photo_list).code
                }
            }
        }
        return code
    }

}