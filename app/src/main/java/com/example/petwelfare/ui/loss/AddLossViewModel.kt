package com.example.petwelfare.ui.loss

import androidx.lifecycle.ViewModel
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class AddLossViewModel : ViewModel() {

    fun sendLoss(
        name: String,
        sex: String,
        type: String,
        address: String,
        contact: String,
        lostTime: String,
        sendTime: String,
        description: String,
        Authorization: String,
        photo_list: List<MultipartBody.Part>
    ) : Int {
        var code = 0
        runBlocking {
            coroutineScope {
                launch {
                    code = PetWelfareNetwork.sendLoss(name, sex, type, address, contact,
                        lostTime, sendTime, description, Authorization, photo_list).code
                }
            }
        }
        return code
    }

}