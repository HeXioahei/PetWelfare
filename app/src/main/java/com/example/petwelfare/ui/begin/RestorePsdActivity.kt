package com.example.petwelfare.ui.begin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityRestorePsdBinding
import com.example.petwelfare.databinding.DialogRestorePsdBinding
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.GlobalScope

class RestorePsdActivity : AppCompatActivity() {

    lateinit var binding : ActivityRestorePsdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePsdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        val viewModel: RestorePsdViewModel by viewModels()

        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.getVerificationBtn.setOnClickListener {
            viewModel.sendMailbox(binding.mailboxInReset.text.toString())
        }

        binding.toPsdBtn.setOnClickListener {
            if (binding.verificationInReset.text.toString() != "") {
                // 打开输入密码的 dialog
                val binding2: DialogRestorePsdBinding = DialogRestorePsdBinding.inflate(layoutInflater)
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setView(binding2.root)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
                binding2.resetBtn.setOnClickListener {
                    val confirmPsdInReset = binding2.confirmPsdInReset.text.toString()
                    val psdInReset = binding2.psdInReset.text.toString()
                    val code = viewModel.resetPsd(
                        binding.mailboxInReset.text.toString(),
                        binding.verificationInReset.text.toString(),
                        psdInReset,
                        confirmPsdInReset
                    )
                    if (code == 200) {
                        Toast.makeText(this, "重置成功", Toast.LENGTH_SHORT).show()
                        ActivityCollector.removeActivityAfterLogin()
                    }
                }
                binding2.cancelBtn.setOnClickListener {
                    alertDialog.dismiss()
                }
            } else {
                Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}