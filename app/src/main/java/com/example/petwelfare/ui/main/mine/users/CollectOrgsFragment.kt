package com.example.petwelfare.ui.main.mine.users

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentCollectOrgsBinding
import com.example.petwelfare.ui.adapter.listadapter.OrgsAdapter
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter


class CollectOrgsFragment : Fragment() {

    private lateinit var binding : FragmentCollectOrgsBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectOrgsBinding.inflate(inflater, container, false)

        val viewModel : FollowsViewModel by viewModels()

        viewModel.getCollectOrgs()
        binding.swipeRefresh.isRefreshing = true

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getCollectOrgs()
        }

        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val orgsAdapter = OrgsAdapter(viewModel.orsList)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = orgsAdapter

        viewModel.collectOrgsList.observe(viewLifecycleOwner) { result->
            if (result.data.org_list.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.orsList.clear()
            viewModel.orsList.addAll(result.data.org_list)
            orgsAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }
}