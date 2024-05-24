package com.example.petwelfare.ui.main.mine.users

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    private var followsList : MutableList<UserBrief> = mutableListOf(UserBrief(), UserBrief(), UserBrief())
    private var orsList : MutableList<Org> = mutableListOf(Org(), Org(), Org())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFollowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FollowsViewModel by viewModels()

        viewModel.followsList.observe(this) { result->
            followsList = result.data
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val followsAdapter = UsersAdapter(followsList, this)
        val orgsAdapter = OrgsAdapter(orsList, this)

        binding.recyclerView.adapter = followsAdapter
        binding.recyclerView.layoutManager = layoutManager

        binding.follows.setOnClickListener {
            binding.recyclerView.adapter = followsAdapter
        }

        binding.orgs.setOnClickListener {
            binding.recyclerView.adapter = orgsAdapter
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }
    }
}