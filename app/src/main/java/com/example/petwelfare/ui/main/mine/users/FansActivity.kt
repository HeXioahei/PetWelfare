package com.example.petwelfare.ui.main.mine.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityFansBinding
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter

class FansActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFansBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FansViewModel by viewModels()

        viewModel.getFans()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val fansAdapter = UsersAdapter(viewModel.fansList, this, this)
        val friendsAdapter = UsersAdapter(viewModel.friendsList, this, this)

        binding.recyclerView.adapter = fansAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.fansCursor.visibility = View.VISIBLE
        binding.friendsCursor.visibility = View.INVISIBLE

        binding.fans.setOnClickListener {
            binding.recyclerView.adapter = fansAdapter
            binding.fansCursor.visibility = View.VISIBLE
            binding.friendsCursor.visibility = View.INVISIBLE
        }

        binding.friends.setOnClickListener {
            binding.recyclerView.adapter = friendsAdapter
            binding.fansCursor.visibility = View.INVISIBLE
            binding.friendsCursor.visibility = View.VISIBLE
        }

        viewModel.fansListLiveData.observe(this) { result->
            viewModel.fansList.clear()
            viewModel.fansList.addAll(result.data.fans)
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