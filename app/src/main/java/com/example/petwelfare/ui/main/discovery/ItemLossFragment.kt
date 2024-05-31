package com.example.petwelfare.ui.main.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentItemLossBinding
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter

class ItemLossFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    private lateinit var binding : FragmentItemLossBinding

    private var address = "福州大学"

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemLossBinding.inflate(inflater, container, false)

        val viewModel : DiscoveryViewModel by viewModels()

        val lossAdapter = LossAdapter(viewModel.lossList)
        binding.lossList.adapter = lossAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.lossList.layoutManager = layoutManager

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getLoss(viewModel.address)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.addressLiveData.observe(viewLifecycleOwner) { result->
            viewModel.address = result
            viewModel.getLoss(viewModel.address)
        }

        viewModel.lossResponse.observe(viewLifecycleOwner) { result->
            viewModel.lossList.clear()
            viewModel.lossList.addAll(result.data)
            lossAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}