package com.example.petwelfare.ui.main.mine.users

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentFansBinding
import com.example.petwelfare.databinding.FragmentFollowsBinding
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter


class FansFragment : Fragment() {

    private lateinit var binding : FragmentFansBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFansBinding.inflate(inflater, container, false)

        val viewModel : FansViewModel by viewModels()

        viewModel.getFans()
        binding.swipeRefresh.isRefreshing = true

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getFans()
        }

        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        val fansAdapter = UsersAdapter(viewModel.fansList)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = fansAdapter

        viewModel.fansListLiveData.observe(viewLifecycleOwner) { result->
            if (result.data.fans.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.fansList.clear()
            viewModel.fansList.addAll(result.data.fans)
            fansAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }
}