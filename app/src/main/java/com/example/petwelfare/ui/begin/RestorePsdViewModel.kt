package com.example.petwelfare.ui.begin

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RestorePsdViewModel : ViewModel() {

    fun sendMailbox(mailbox2: String) : Int {
        var code = 0
        runBlocking {
            coroutineScope {
                launch {
                    code = PetWelfareNetwork.getVerification("pwd", mailbox2).code

                }
            }
        }
        return code
    }

    fun resetPsd(mailbox2: String, verification: String, psd: String, confirmPsd: String) : Int {
        var code = 0
        if (psd == confirmPsd) {
            runBlocking {
                coroutineScope {
                    launch {
                        code = PetWelfareNetwork.resetPassword(mailbox2, verification, psd).code

                    }
                }
            }
        } else {
            code = 1
        }
        return code
    }
}