package com.example.petwelfare.ui.begin

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.opengl.Visibility
import android.os.Bundle
import android.text.InputType
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
import com.example.petwelfare.logic.model.MailboxList
import com.example.petwelfare.ui.listadapter.MailboxAdapter
import java.lang.reflect.Type

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
            Log.d("dianji1","dianji1")
            val code = viewModel.sendMailbox(binding.registerMailbox.text.toString())
            if (code == 200) {
                binding.getVerificationBtn.text = "已发送请求"
                binding.getVerificationBtn.setTextColor(resources.getColor(R.color.gray))
                Log.d("SendMailbox", "success")
                Toast.makeText(PetWelfareApplication.context, "请求已发送", Toast.LENGTH_SHORT).show()
            } else {
                Log.d("SendMailbox", "failure")
                Toast.makeText(PetWelfareApplication.context, "请求发送失败", Toast.LENGTH_SHORT).show()
            }
            Log.d("dianji","dianji")
        }

        // 注册
        binding.registerBtn.setOnClickListener {
            val mailbox = binding.registerMailbox.text.toString()
            val psd = binding.registerPsd.text.toString()
            val verification = binding.verification.text.toString()
            val code = viewModel.register(mailbox, psd, verification)
            if (code == 200) {
                binding.registerMailbox.setText("")
                binding.registerPsd.setText("")
                binding.registerPsdConfirm.setText("")
                binding.verification.setText("")
                activity.replaceFragment("login")
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
                binding.showMenuBtn.background = resources.getDrawable(R.drawable.btn_up_menu)
                binding.dropdownMenuContainer.visibility = View.VISIBLE
            } else {
                binding.showMenuBtn.background = resources.getDrawable(R.drawable.btn_menu)
                binding.dropdownMenuContainer.visibility = View.INVISIBLE
            }
        }

        return binding.root
    }

}