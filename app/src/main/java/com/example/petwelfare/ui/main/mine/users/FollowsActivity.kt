package com.example.petwelfare.ui.main.mine.users

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityFansBinding
import com.example.petwelfare.databinding.ActivityFollowsBinding
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.ui.adapter.listadapter.OrgsAdapter
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter

class FollowsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFollowsBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFollowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FollowsViewModel by viewModels()

        viewModel.getFollows()

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val followsAdapter = UsersAdapter(viewModel.followsList, this, this)
        val orgsAdapter = OrgsAdapter(viewModel.orsList, this)

        binding.recyclerView.adapter = followsAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.followsCursor.visibility = View.VISIBLE
        binding.orgsCursor.visibility = View.INVISIBLE

        binding.follows.setOnClickListener {
            binding.recyclerView.adapter = followsAdapter
            binding.followsCursor.visibility = View.VISIBLE
            binding.orgsCursor.visibility = View.INVISIBLE
        }

        binding.orgs.setOnClickListener {
            binding.recyclerView.adapter = orgsAdapter
            binding.followsCursor.visibility = View.INVISIBLE
            binding.orgsCursor.visibility = View.VISIBLE
        }

        viewModel.followsListLiveData.observe(this) { result->
            viewModel.followsList.clear()
            viewModel.followsList.addAll(result.data.follows)
            followsAdapter.notifyDataSetChanged()
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