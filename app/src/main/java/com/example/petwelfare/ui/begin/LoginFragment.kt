package com.example.petwelfare.ui.begin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentLoginBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.MailboxList
import com.example.petwelfare.ui.listadapter.MailboxAdapter

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
            viewModel.setas(mailbox, psd)
            //val code = viewModel.login(mailbox, psd)
//            Log.d("aa","aa")
//            if (code == 200) {
//                Repository.mailbox = mailbox
//                activity.toMainActivity()
//            }
        }

        viewModel.mmmm.observe(activity) { result ->
            val response = result.getOrNull()
            if (response != null) {
                Repository.refreshToken = response.refresh_token
                Repository.Authorization = response.access_token
                Repository.myId = response.id
            } else {
                result.exceptionOrNull()?.printStackTrace()
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