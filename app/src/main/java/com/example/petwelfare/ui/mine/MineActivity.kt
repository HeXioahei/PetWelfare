package com.example.petwelfare.ui.mine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityMineBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.mine.edit.EditMyInfoActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        // 设置底部总导航栏
        val navigationView: BottomNavigationView = binding.bottomNavigationView as BottomNavigationView
        // 1、先拿 NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        // 2、再通过 navHostFragment 来拿 NavController
        val navController: NavController = navHostFragment.navController
        // 3、然后将 navigationView 和 navController 绑定
        navigationView.setupWithNavController(navController)
        // 将 item 背景色调为透明
        //navigationView.itemIconTintList = null

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
        viewModel.setUserId(Repository.userId)

        // 当 myDetailData 中有任何数据变化时，就会回调传入的这个Observe接口中
        viewModel.myDetailData.observe(this) { result ->
            val user = result.getOrNull()
            if (user != null) {
                viewModel.myDetail = user
                // 进行页面呈现的变化

                // 设置头像
                val myHeadImageString = viewModel.myDetail.headImage
                val glideUrl = GlideUrl(
                    myHeadImageString,
                    LazyHeaders.Builder()
                        .addHeader("Authorization", Repository.Authorization)
                        .build()
                )
                binding.headImage?.let { Glide.with(this).load(glideUrl).into(it) }
                // 设置其他信息
                binding.username?.text = viewModel.myDetail.username
                binding.address?.text = viewModel.myDetail.address
                binding.personality?.text = viewModel.myDetail.personality
                binding.fansNum?.text = viewModel.myDetail.fanNums.toString()
                binding.followsNum?.text = viewModel.myDetail.followNums.toString()
                binding.integralsNum?.text = viewModel.myDetail.integral.toString()
                binding.telephone?.text = viewModel.myDetail.telephone

            } else {
                Toast.makeText(this, "未能获取用户信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        }

        // 下拉刷新，更新用户信息
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.myDetailData = Repository                      // 不确定此处是否有问题
                .getUserInfo(Repository.userId, Repository.Authorization)
        }

        // 跳转到编辑页
        binding.edit?.setOnClickListener {
            val intent = Intent(this, EditMyInfoActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}