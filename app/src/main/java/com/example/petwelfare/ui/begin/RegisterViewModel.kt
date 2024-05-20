package com.example.petwelfare.ui.begin

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class RegisterViewModel : ViewModel() {

    fun sendMailbox(mailbox2: String) : Int {
        var code = 0
        Log.d("sendMailbox","sendMailbox")
        runBlocking {
            Log.d("sendMailbox","sendMailbox")
            coroutineScope {
                launch {
                    Log.d("sendMailbox","sendMailbox")
                    code = PetWelfareNetwork.getVerification("register", mailbox2).code

                }
            }

        }
        return code
    }

    fun register(mailbox2: String, psd: String, verification: String): Int {
        var code = 0
        runBlocking {
            coroutineScope {
                launch {
                    code = PetWelfareNetwork.register(mailbox2, psd, verification).code
                    if (code == 200) {
                        Log.d("register", "success")
                        Toast.makeText(PetWelfareApplication.context, "注册成功", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("register", "failure")
                        Toast.makeText(PetWelfareApplication.context, "注册失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        return code
    }
}