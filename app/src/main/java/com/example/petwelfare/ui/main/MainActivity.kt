package com.example.petwelfare.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityMainBinding
import com.example.petwelfare.databinding.DialogAdvertiseBinding
import com.example.petwelfare.databinding.DialogEditInfoBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.ui.main.add.AddActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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

        // 刷新token
        Repository.tokenLiveData.observe(this) {token->
            Repository.Authorization = token
            Repository.refreshToken = ""
            CoroutineScope(Dispatchers.IO).launch {
                MineDao.saveToken(Repository.Authorization, Repository.refreshToken)
            }
        }


        if (Repository.advertiseFlag) {

            val alertDialog = AlertDialog.Builder(this).create()

            val binding : DialogAdvertiseBinding = DialogAdvertiseBinding.inflate(layoutInflater)
            alertDialog.setView(binding.root)

            binding.text.setOnClickListener {
                alertDialog.dismiss()
                Repository.advertiseFlag = false
            }

            alertDialog.show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        ActivityCollector.removeAll()
    }
}