package com.example.petwelfare.ui.begin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityRestorePsdBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.utils.MailboxList
import com.example.petwelfare.ui.adapter.listadapter.MailboxAdapter
import com.example.petwelfare.ui.begin.viewmodel.RestorePsdViewModel

class RestorePsdActivity : AppCompatActivity() {

    lateinit var binding : ActivityRestorePsdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestorePsdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        val viewModel: RestorePsdViewModel by viewModels()

        // 返回
        binding.returnBtn.setOnClickListener {
            finish()
        }

        //获取验证码
        binding.getVerificationBtn.setOnClickListener {
            viewModel.getVerification(binding.mailboxInReset.text.toString())
        }
        viewModel.getVerificationResponseCode.observe(this) { result->
            if (result == 200) {
                binding.getVerificationBtn.text = "已发送请求"
                binding.getVerificationBtn.setTextColor(resources.getColor(R.color.gray))
                Log.d("getVerification", "success")
                Toast.makeText(PetWelfareApplication.context, "请求已发送", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("getVerification", "failure")
                Toast.makeText(PetWelfareApplication.context, "请求发送失败", Toast.LENGTH_SHORT).show()
            }
        }


        // 显示密码
        binding.showPsdBtn1.setOnClickListener {
            if (binding.psdInReset.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.psdInReset.transformationMethod = null
            } else {
                binding.psdInReset.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        binding.showPsdBtn2.setOnClickListener {
            if (binding.psdConfirmInReset.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.psdConfirmInReset.transformationMethod = null
            } else {
                binding.psdConfirmInReset.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        val mailboxAdapter = MailboxAdapter(MailboxList.mailboxList(), binding.mailboxInReset, binding.dropdownMenuContainer, binding.showMenuBtn)
        binding.dropdownMenu.adapter = mailboxAdapter
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.dropdownMenu.layoutManager = layoutManager

        // 选择邮箱类型
        binding.showMenuBtn.setOnClickListener {
            if (binding.dropdownMenuContainer.visibility == View.INVISIBLE) {
                binding.showMenuBtn.background = resources.getDrawable(R.drawable.btn_menu)
                binding.dropdownMenuContainer.visibility = View.VISIBLE
            } else {
                binding.showMenuBtn.background = resources.getDrawable(R.drawable.btn_menu)
                binding.dropdownMenuContainer.visibility = View.INVISIBLE
            }
        }

        // 确认修改
        binding.restoreBtn.setOnClickListener {
            val psd = binding.psdInReset.text.toString()
            val psdConfirm = binding.psdConfirmInReset.text.toString()
            if (psd != psdConfirm) {
                Toast.makeText(PetWelfareApplication.context, "密码不一致，请重新确认密码", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("psd1", binding.psdInReset.text.toString())
                Log.d("verification", binding.verification.text.toString())
                viewModel.resetPsd(
                    binding.mailboxInReset.text.toString(),
                    psd,
                    binding.verification.text.toString()
                )
            }
        }
        viewModel.resetPsdResponse.observe(this) { result->
            when (result.code) {
                200 -> {
                    Log.d("resetPsd", "success")
                    Toast.makeText(PetWelfareApplication.context, "修改密码成功", Toast.LENGTH_SHORT).show()
                    Log.d("psd2", binding.psdInReset.text.toString())
                    Repository.exit()
                }
                else -> {
                    Log.d("resetPsd", "failure")
                    Toast.makeText(PetWelfareApplication.context, "修改密码失败", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}