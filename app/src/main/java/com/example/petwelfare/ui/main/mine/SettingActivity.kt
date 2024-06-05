package com.example.petwelfare.ui.main.mine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivitySettingBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.exit.setOnClickListener {
            Repository.exit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}