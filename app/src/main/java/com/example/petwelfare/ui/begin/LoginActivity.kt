package com.example.petwelfare.ui.begin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityLoginBinding
import com.example.petwelfare.ui.MainActivity
import com.example.petwelfare.ui.mine.MineActivity

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

        binding.toLoginBtn.setOnClickListener {
            replaceFragment("login")
        }
        binding.toRegisterBtn.setOnClickListener {
            replaceFragment("register")
        }

    }

    fun replaceFragment(type: String) {
        // 创建FragmentTransaction实例
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        // 添加fragment
        if (type == "login") transaction.replace(R.id.fragment, loginFragment)
        else transaction.replace(R.id.fragment, registerFragment)
        // 提交事务
        transaction.commit()
    }

    fun toMainActivity() {
        val intent = Intent(this, MineActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}