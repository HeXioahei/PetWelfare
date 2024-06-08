package com.example.petwelfare.ui.main.mine.users.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.R
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityFansBinding
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter
import com.example.petwelfare.ui.main.mine.users.viewmodel.FansViewModel

class FansActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFansBinding


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFansBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FansViewModel by viewModels()

        viewModel.getFans()
        binding.swipeRefresh.isRefreshing = true

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getFans()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val fansAdapter = UsersAdapter(viewModel.fansList)
        val friendsAdapter = UsersAdapter(viewModel.friendsList)

        binding.recyclerView.adapter = fansAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.fans.typeface = ResourcesCompat.getFont(this, R.font.mf)
        binding.friends.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
        binding.fansCursor.visibility = View.VISIBLE
        binding.friendsCursor.visibility = View.INVISIBLE

        binding.fans.setOnClickListener {
            binding.recyclerView.adapter = fansAdapter
            if (viewModel.fansList.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            binding.fans.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.friends.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
            binding.fansCursor.visibility = View.VISIBLE
            binding.friendsCursor.visibility = View.INVISIBLE
        }

        binding.friends.setOnClickListener {
            binding.recyclerView.adapter = friendsAdapter
            if (viewModel.friendsList.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            binding.friends.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.fans.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
            binding.fansCursor.visibility = View.INVISIBLE
            binding.friendsCursor.visibility = View.VISIBLE
        }

        viewModel.fansListLiveData.observe(this) { result->
            if (binding.recyclerView.adapter == fansAdapter) {
                if (result.data.fans.isNotEmpty()) binding.image.visibility = View.INVISIBLE
                else binding.image.visibility = View.VISIBLE
            }
            viewModel.fansList.clear()
            viewModel.fansList.addAll(result.data.fans)
            fansAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
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