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

    fun sendMailbox(mailbox2: String) {
        runBlocking {
            coroutineScope {
                launch {
                    val code = PetWelfareNetwork.getVerification(mailbox2).code
                    if (code == 200) {
                        Log.d("SendMailbox", "success")
                    } else {
                        Log.d("SendMailbox", "failure")
                    }
                }
            }
        }
    }

    fun resetPsd(mailbox2: String, verification: String, psd: String, confirmPsd: String){
        if (psd == confirmPsd) {
            runBlocking {
                coroutineScope {
                    launch {
                        val code = PetWelfareNetwork.resetPassword(mailbox2, verification, psd).code
                        if (code == 200) {
                            Log.d("resetPsd", "success")
                            Toast.makeText(PetWelfareApplication.context, "重置成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("resetPsd", "failure")
                            Toast.makeText(PetWelfareApplication.context, "重置失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(PetWelfareApplication.context, "密码不一致，请重新确认密码", Toast.LENGTH_SHORT).show()
        }
    }
}