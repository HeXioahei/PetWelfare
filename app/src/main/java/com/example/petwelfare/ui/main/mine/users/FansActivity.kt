package com.example.petwelfare.ui.main.mine.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityFansBinding
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter

class FansActivity : AppCompatActivity() {

    lateinit var binding : ActivityFansBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FansViewModel by viewModels()

        viewModel.fansData.observe(this) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.fans = list
            }
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val fansAdapter = UsersAdapter(viewModel.fans, this)
        binding.fansList.adapter = fansAdapter
        binding.fansList.layoutManager = layoutManager

        binding.returnBtn.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}