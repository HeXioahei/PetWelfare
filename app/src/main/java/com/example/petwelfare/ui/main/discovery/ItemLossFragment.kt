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
import com.example.petwelfare.databinding.FragmentItemLossBinding
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter

class ItemLossFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    private lateinit var binding : FragmentItemLossBinding
    private var lossList: MutableList<Loss> = mutableListOf(Loss(), Loss(), Loss(), Loss(), Loss())

    private var address = "福州大学"

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemLossBinding.inflate(inflater, container, false)

        val viewModel : DiscoveryViewModel by viewModels()

        val lossAdapter = LossAdapter(lossList, mainActivity)
        binding.lossList.adapter = lossAdapter
        val layoutManager = LinearLayoutManager(mainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.lossList.layoutManager = layoutManager

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getLoss(address)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.address.observe(mainActivity) { result->
            address = result
        }

        viewModel.lossResponse.observe(mainActivity) { result->
            lossList = result.data
            lossAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}