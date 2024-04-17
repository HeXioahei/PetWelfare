package com.example.petwelfare.ui.mine

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody

class EditMyInfoViewModel : ViewModel() {
    fun changeHead(headImage: MultipartBody.Part, Authorization: String) {
        runBlocking {
            coroutineScope {
                launch {
                    val code = PetWelfareNetwork.changeHead(headImage, Authorization).code
                    if (code == 200) {
                        Log.d("changeHead", "success")
                    } else {
                        Log.d("changeHead", "failure")
                    }
                }
            }
        }
    }

    fun changeUsername(username: String, Authorization: String) {
        runBlocking {
            coroutineScope {
                launch {
                    val code = PetWelfareNetwork.changeUsername(username, Authorization).code
                    if (code == 200) {
                        Log.d("changeUsername", "success")
                    } else {
                        Log.d("changeUsername", "failure")
                    }
                }
            }
        }
    }

    fun changeAddress(address: String, Authorization: String) {
        runBlocking {
            coroutineScope {
                launch {
                    val code = PetWelfareNetwork.changeAddress(address, Authorization).code
                    if (code == 200) {
                        Log.d("changeAddress", "success")
                    } else {
                        Log.d("changeAddress", "failure")
                    }
                }
            }
        }
    }

    fun changeTelephone(telephone: String, Authorization: String) {
        runBlocking {
            coroutineScope {
                launch {
                    val code = PetWelfareNetwork.changeTelephone(telephone, Authorization).code
                    if (code == 200) {
                        Log.d("changeTelephone", "success")
                    } else {
                        Log.d("changeTelephone", "failure")
                    }
                }
            }
        }
    }

    fun changePersonality(personality: String, Authorization: String) {
        runBlocking {
            coroutineScope {
                launch {
                    val code = PetWelfareNetwork.changePersonality(personality, Authorization).code
                    if (code == 200) {
                        Log.d("changePersonality", "success")
                    } else {
                        Log.d("changePersonality", "failure")
                    }
                }
            }
        }
    }
}