package com.example.petwelfare.ui.begin.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentRegisterBinding
import com.example.petwelfare.utils.MailboxList
import com.example.petwelfare.ui.adapter.listadapter.MailboxAdapter
import com.example.petwelfare.ui.begin.viewmodel.RegisterViewModel
import com.example.petwelfare.ui.begin.activity.LoginActivity

class RegisterFragment(private val activity: LoginActivity) : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        // 获取验证码
        binding.getVerificationBtn.setOnClickListener {
            viewModel.getVerification(binding.registerMailbox.text.toString())
        }
        viewModel.getVerificationResponseCode.observe(activity) { result->
            Log.d("getVerificationResponseCode",result.toString())
            if (result == 200) {
                Log.d("getVerification", "success")
                binding.getVerificationBtn.text = "已发送请求"
                binding.getVerificationBtn.setTextColor(resources.getColor(R.color.gray))
                Toast.makeText(PetWelfareApplication.context, "请求已发送", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("getVerification", "failure")
                Toast.makeText(PetWelfareApplication.context, "请求失败", Toast.LENGTH_SHORT).show()
            }
        }


        // 注册
        binding.registerBtn.setOnClickListener {
            val mailbox = binding.registerMailbox.text.toString()
            val psd = binding.registerPsd.text.toString()
            val verification = binding.verification.text.toString()
            viewModel.register(mailbox, psd, verification)
        }
        viewModel.registerResponseCode.observe(activity) { result ->
            Log.d("registerResponseCode",result.toString())
            if (result == 200) {
                Log.d("register", "success")
                Toast.makeText(PetWelfareApplication.context, "注册成功", Toast.LENGTH_SHORT).show()
                binding.registerMailbox.setText("")
                binding.registerPsd.setText("")
                binding.registerPsdConfirm.setText("")
                binding.verification.setText("")
                activity.replaceFragment("login")
            } else {
                Log.d("register", "failure")
                Toast.makeText(PetWelfareApplication.context, "注册失败", Toast.LENGTH_SHORT).show()
            }
        }

        // 显示密码
        binding.showPsdBtn1.setOnClickListener {
            if (binding.registerPsd.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.registerPsd.transformationMethod = null
            } else {
                binding.registerPsd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        binding.showPsdBtn2.setOnClickListener {
            if (binding.registerPsdConfirm.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.registerPsdConfirm.transformationMethod = null

            } else {
                binding.registerPsdConfirm.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        val mailboxAdapter = MailboxAdapter(MailboxList.mailboxList(), binding.registerMailbox, binding.dropdownMenuContainer, binding.showMenuBtn)
        binding.dropdownMenu.adapter = mailboxAdapter
        val layoutManager = LinearLayoutManager(activity)
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

        return binding.root
    }

}