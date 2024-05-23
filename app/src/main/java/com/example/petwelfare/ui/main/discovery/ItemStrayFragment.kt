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
import com.example.petwelfare.databinding.FragmentItemStrayBinding
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter


class ItemStrayFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    private lateinit var binding : FragmentItemStrayBinding

    private var strayList: MutableList<Stray> = mutableListOf(Stray(), Stray(), Stray(), Stray(), Stray())

    private var address = "福州大学"

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemStrayBinding.inflate(inflater, container, false)

        val viewModel : DiscoveryViewModel by viewModels()

        val strayAdapter = StrayAdapter(strayList, mainActivity)
        binding.strayList.adapter = strayAdapter
        val layoutManager = LinearLayoutManager(mainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.strayList.layoutManager = layoutManager

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getStray(address)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.address.observe(mainActivity) { result->
            address = result
            viewModel.getStray(address)
        }

        viewModel.strayResponse.observe(mainActivity) { result->
            strayList = result.data
            strayAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}