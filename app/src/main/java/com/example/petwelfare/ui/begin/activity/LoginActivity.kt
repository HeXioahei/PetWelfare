package com.example.petwelfare.ui.begin.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityLoginBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.begin.fragment.LoginFragment
import com.example.petwelfare.ui.begin.viewmodel.LoginViewModel
import com.example.petwelfare.ui.begin.fragment.RegisterFragment
import com.example.petwelfare.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels()
    private val fragmentManager: FragmentManager = supportFragmentManager
    private val loginFragment = LoginFragment(this)
    private val registerFragment = RegisterFragment(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        replaceFragment("login")
        binding.loginCursor.visibility = View.VISIBLE

        // 更换到登录页
        binding.toLoginBtn.setOnClickListener {
            replaceFragment("login")
        }

        // 更换到注册页
        binding.toRegisterBtn.setOnClickListener {
            replaceFragment("register")
        }

    }

    // 更换fragment
    @SuppressLint("ResourceAsColor")
    fun replaceFragment(type: String) {

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()

        if (type == "login") {
            transaction.replace(R.id.fragmentLogin, loginFragment)
            binding.loginCursor.visibility = View.VISIBLE
            binding.registerCursor.visibility = View.INVISIBLE
            binding.toLoginBtn.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.toRegisterBtn.typeface = ResourcesCompat.getFont(this, R.font.source_regular)
        }
        else {
            transaction.replace(R.id.fragmentLogin, registerFragment)
            binding.loginCursor.visibility = View.INVISIBLE
            binding.registerCursor.visibility = View.VISIBLE
            binding.toRegisterBtn.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.toLoginBtn.typeface = ResourcesCompat.getFont(this, R.font.source_regular)
        }
        // 提交事务
        transaction.commit()
    }

    // 成功登录，跳转页面
    fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        Repository.advertiseFlag = true
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.removeAll()
    }

}