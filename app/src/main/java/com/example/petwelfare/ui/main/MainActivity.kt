package com.example.petwelfare.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCollector.addActivity(this)
        ActivityCollector.mainActivity = this

        // 设置底部总导航栏
        val navigationView = binding.bottomNavigationViewMain
        // 1、先拿 NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_main) as NavHostFragment
        // 2、再通过 navHostFragment 来拿 NavController
        val navController: NavController = navHostFragment.navController
        // 3、然后将 navigationView 和 navController 绑定
        navigationView.setupWithNavController(navController)
        // 将 item 背景色调为透明
        navigationView.itemIconTintList = null

        binding.toWriteBtn.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}