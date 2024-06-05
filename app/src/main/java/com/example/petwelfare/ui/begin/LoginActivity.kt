package com.example.petwelfare.ui.begin

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
import com.example.petwelfare.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModels()
    // 获取FragmentManager
    private val fragmentManager: FragmentManager = supportFragmentManager
    // 创建Fragment实例
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
        // 创建FragmentTransaction实例
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        // 添加fragment
        if (type == "login") {
            transaction.replace(R.id.fragmentLogin, loginFragment)
            binding.loginCursor.visibility = View.VISIBLE
            binding.registerCursor.visibility = View.INVISIBLE
//            binding.toLoginBtn.setTextColor(R.color.active2)
            binding.toLoginBtn.typeface = ResourcesCompat.getFont(this, R.font.mf)
//            binding.toRegisterBtn.setTextColor(R.color.text_gray)
            binding.toRegisterBtn.typeface = ResourcesCompat.getFont(this, R.font.source_regular)
        }
        else {
            transaction.replace(R.id.fragmentLogin, registerFragment)
            binding.loginCursor.visibility = View.INVISIBLE
            binding.registerCursor.visibility = View.VISIBLE
//            binding.toRegisterBtn.setTextColor(R.color.active2)
            binding.toRegisterBtn.typeface = ResourcesCompat.getFont(this, R.font.mf)
//            binding.toLoginBtn.setTextColor(R.color.text_gray)
            binding.toLoginBtn.typeface = ResourcesCompat.getFont(this, R.font.source_regular)
        }
        // 提交事务
        transaction.commit()
    }

    // 成功登录，跳转页面
    fun toMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.removeAll()
    }

}