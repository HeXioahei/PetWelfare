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
                    val code = PetWelfareNetwork.getVerification("pwd", mailbox2).code
                    if (code == 200) {
                        Log.d("SendMailbox", "success")
                        Toast.makeText(PetWelfareApplication.context, "请求已发送", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.d("SendMailbox", "failure")
                        Toast.makeText(PetWelfareApplication.context, "请求发送失败", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun resetPsd(mailbox2: String, verification: String, psd: String, confirmPsd: String) : Int {
        var code = 0
        if (psd == confirmPsd) {
            runBlocking {
                //coroutineScope {
                    launch {
                        code = PetWelfareNetwork.resetPassword(mailbox2, verification, psd).code
                        if (code == 200) {
                            Log.d("resetPsd", "success")
                            Toast.makeText(PetWelfareApplication.context, "重置成功", Toast.LENGTH_SHORT).show()
                        } else {
                            Log.d("resetPsd", "failure")
                            Toast.makeText(PetWelfareApplication.context, "重置失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                //}
            }
            return code
        } else {
            Toast.makeText(PetWelfareApplication.context, "密码不一致，请重新确认密码", Toast.LENGTH_SHORT).show()
            return code
        }
    }
}