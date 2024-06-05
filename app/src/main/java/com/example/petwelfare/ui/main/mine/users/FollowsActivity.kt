package com.example.petwelfare.ui.main.mine.users

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.R
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityFollowsBinding
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
        viewModel.getCollectOrgs()
        binding.swipeRefresh.isRefreshing = true
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getFollows()
            viewModel.getCollectOrgs()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val followsAdapter = UsersAdapter(viewModel.followsList, this, this)
        val orgsAdapter = OrgsAdapter(viewModel.orsList)

        binding.recyclerView.adapter = followsAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.follows.typeface = ResourcesCompat.getFont(this, R.font.mf)
        binding.orgs.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
        binding.followsCursor.visibility = View.VISIBLE
        binding.orgsCursor.visibility = View.INVISIBLE

        binding.follows.setOnClickListener {
            binding.recyclerView.adapter = followsAdapter
            binding.follows.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.orgs.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
            binding.followsCursor.visibility = View.VISIBLE
            binding.orgsCursor.visibility = View.INVISIBLE
        }

        binding.orgs.setOnClickListener {
            binding.recyclerView.adapter = orgsAdapter
            binding.orgs.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.follows.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
            binding.followsCursor.visibility = View.INVISIBLE
            binding.orgsCursor.visibility = View.VISIBLE
        }

        viewModel.followsListLiveData.observe(this) { result->
            if (result.data.follows.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.followsList.clear()
            viewModel.followsList.addAll(result.data.follows)
            followsAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.collectOrgsList.observe(this) { result->
            viewModel.orsList.clear()
            viewModel.orsList.addAll(result.data.org_list)
            followsAdapter.notifyDataSetChanged()
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