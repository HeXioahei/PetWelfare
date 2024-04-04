package com.example.petwelfare.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityMineBinding

class MineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 创建viewModel
        val viewModel: MineViewModel by viewModels()
        // 参考官方文档：
        // https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh-cn
        // 需引入依赖：implementation("androidx.activity:activity-ktx:1.7.0")
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same DiceRollViewModel instance created by the first activity.
        // Use the 'by viewModels()' Kotlin property delegate
        // from the activity-ktx artifact

        // 应用 viewModel
        // 参看书中第623页
        viewModel.myDetailData.observe(this, Observer { result ->
            val user = result.getOrNull()
            if (user != null) {
                // 进行页面呈现的变化
                // 比如用返回的数据来更改页面显示的用户名
            } else {
                Toast.makeText(this, "未能获取用户信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
}