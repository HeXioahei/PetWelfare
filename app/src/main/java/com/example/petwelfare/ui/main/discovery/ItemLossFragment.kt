package com.example.petwelfare.ui.main.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentItemLossBinding
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter

class ItemLossFragment : Fragment() {

    private lateinit var binding : FragmentItemLossBinding


    val viewModel : DiscoveryViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemLossBinding.inflate(inflater, container, false)

        val lossAdapter = LossAdapter(viewModel.lossList)
        binding.lossList.adapter = lossAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.lossList.layoutManager = layoutManager

        binding.swipeRefresh.isRefreshing = true
        viewModel.getLoss(DiscoveryViewModel.address)

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getLoss(DiscoveryViewModel.address)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        DiscoveryViewModel.addressLiveData.observe(viewLifecycleOwner) { result->
            DiscoveryViewModel.address = result
            viewModel.getLoss(DiscoveryViewModel.address)
        }

        viewModel.lossResponse.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.lossList.clear()
            viewModel.lossList.addAll(result.data)
            lossAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getLoss(DiscoveryViewModel.address)
    }
}