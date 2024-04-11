package com.example.petwelfare.ui.begin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityLoginBinding
import com.example.petwelfare.logic.network.PetWelfareNetwork

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取FragmentManager
        val fragmentManager: FragmentManager = supportFragmentManager
        // 创建Fragment实例
        val loginFragment = LoginFragment()
        val registerFragment = RegisterFragment()
        // 创建FragmentTransaction实例
        var transaction: FragmentTransaction = fragmentManager.beginTransaction()
        // 添加fragment
        transaction.add(R.id.fragment, loginFragment)
        // 提交事务
        transaction.commit()

        binding.toLoginBtn.setOnClickListener {
            // 使用replace方法替换Fragment容器中的Fragment
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, loginFragment)
            transaction.commit()
        }
        binding.toRegisterBtn.setOnClickListener {
            transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment, registerFragment)
            transaction.commit()
        }

    }
}