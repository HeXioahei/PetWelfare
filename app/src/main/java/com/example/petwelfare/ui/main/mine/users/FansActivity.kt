package com.example.petwelfare.ui.main.mine.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityFansBinding
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter

class FansActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFansBinding
    private var fansList : MutableList<UserBrief> = mutableListOf(UserBrief(), UserBrief(),UserBrief())
    private var friendsList : MutableList<UserBrief> = mutableListOf(UserBrief(), UserBrief(),UserBrief())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FansViewModel by viewModels()

        viewModel.getFans()

        viewModel.fansList.observe(this) { result->
            fansList = result.data
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val fansAdapter = UsersAdapter(fansList, this)
        val friendsAdapter = UsersAdapter(friendsList, this)

        binding.recyclerView.adapter = fansAdapter
        binding.recyclerView.layoutManager = layoutManager

        binding.fans.setOnClickListener {
            binding.recyclerView.adapter = fansAdapter
        }

        binding.fans.setOnClickListener {
            binding.recyclerView.adapter = friendsAdapter
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}