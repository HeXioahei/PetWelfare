package com.example.petwelfare.ui.main.discovery.fragment.item

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentItemRescueBinding
import com.example.petwelfare.ui.adapter.listadapter.OrgsAdapter
import com.example.petwelfare.ui.main.discovery.viewmodel.DiscoveryViewModel


class ItemRescueFragment : Fragment() {

    private lateinit var binding : FragmentItemRescueBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemRescueBinding.inflate(inflater, container, false)

        val viewModel : DiscoveryViewModel by viewModels()

        val orgsAdapter = OrgsAdapter(viewModel.orgsList)
        binding.orgsList.adapter = orgsAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.orgsList.layoutManager = layoutManager

        viewModel.getOrgs()
        binding.swipeRefresh.isRefreshing = true

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getOrgs()
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.orgsResponse.observe(viewLifecycleOwner) { result->
            if (result.data.org_list.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.orgsList.clear()
            viewModel.orgsList.addAll(result.data.org_list)
            orgsAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }

}