package com.example.petwelfare.ui.main.mine.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityFansBinding
import com.example.petwelfare.databinding.ActivityFollowsBinding
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter

class FollowsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFollowsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFollowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FollowsViewModel by viewModels()

        viewModel.followsData.observe(this) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.follows = list
            }
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val fansAdapter = UsersAdapter(viewModel.follows, this)
        binding.followsList.adapter = fansAdapter
        binding.followsList.layoutManager = layoutManager

        binding.returnBtn.setOnClickListener {
            finish()
        }
    }
}