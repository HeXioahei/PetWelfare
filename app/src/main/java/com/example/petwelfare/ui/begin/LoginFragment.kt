package com.example.petwelfare.ui.begin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentLoginBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.logic.model.MailboxList
import com.example.petwelfare.ui.adapter.listadapter.MailboxAdapter

@Suppress("DEPRECATION")
class LoginFragment(private val activity: LoginActivity) : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // 登录
        binding.loginBtn.setOnClickListener {
            val mailbox = binding.loginMailbox.text.toString()
            val psd = binding.loginPassword.text.toString()
            viewModel.login(mailbox, psd)
        }

        viewModel.loginResult.observe(activity) { result ->
            Log.d("loginResultCode",result.code.toString())
            if (result.code == 200) {
                Log.d("login", "success")
                Toast.makeText(PetWelfareApplication.context, "登录成功", Toast.LENGTH_SHORT).show()
                Log.d("accessToken", result.data.access_token)
                Repository.Authorization = result.data.access_token
                Repository.myId = result.data.id
                Repository.refreshToken = result.data.refresh_token
                Repository.mailbox = binding.loginMailbox.toString()
                activity.toMainActivity()
            } else {
                Log.d("login", "failure")
                Toast.makeText(PetWelfareApplication.context, "登录失败", Toast.LENGTH_SHORT).show()
            }
        }

        // 找回密码
        binding.toRestorePsd.setOnClickListener {
            val intent = Intent(activity, RestorePsdActivity::class.java)
            startActivity(intent)
        }

        // 显示密码
        binding.showPsdBtn.setOnClickListener {
            if (binding.loginPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.loginPassword.transformationMethod = null
            } else {
                binding.loginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        // 设置邮箱选项
        val mailboxAdapter = MailboxAdapter(MailboxList.mailboxList(), binding.loginMailbox, binding.dropdownMenuContainer, binding.showMenuBtn)
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