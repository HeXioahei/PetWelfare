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
import com.example.petwelfare.databinding.FragmentItemStrayBinding
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter


class ItemStrayFragment : Fragment() {

    private lateinit var binding : FragmentItemStrayBinding

    val viewModel : DiscoveryViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemStrayBinding.inflate(inflater, container, false)


        val strayAdapter = StrayAdapter(viewModel.strayList)
        binding.strayList.adapter = strayAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.strayList.layoutManager = layoutManager


        binding.swipeRefresh.isRefreshing = true
        viewModel.getStray(DiscoveryViewModel.address)
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getStray(DiscoveryViewModel.address)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        DiscoveryViewModel.addressLiveData.observe(viewLifecycleOwner) { result->
            DiscoveryViewModel.address = result
            viewModel.getStray(DiscoveryViewModel.address)
        }

        viewModel.strayResponse.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.strayList.clear()
            viewModel.strayList.addAll(result.data)
            strayAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getStray(DiscoveryViewModel.address)
    }
}