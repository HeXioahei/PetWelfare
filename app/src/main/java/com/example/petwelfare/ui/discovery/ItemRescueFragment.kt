package com.example.petwelfare.ui.discovery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentItemAdoptionBinding
import com.example.petwelfare.databinding.FragmentItemLossBinding
import com.example.petwelfare.databinding.FragmentItemRescueBinding
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.ui.MainActivity
import com.example.petwelfare.ui.listadapter.OrgsAdapter
import com.example.petwelfare.ui.listadapter.StrayAdapter


class ItemRescueFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    private lateinit var binding : FragmentItemRescueBinding

    private var orgsList: MutableList<Org> = mutableListOf(Org(), Org(), Org(), Org(), Org())

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemRescueBinding.inflate(inflater, container, false)

        val viewModel : DiscoveryViewModel by viewModels()

        val orgsAdapter = OrgsAdapter(orgsList, mainActivity)
        binding.orgsList.adapter = orgsAdapter
        val layoutManager = LinearLayoutManager(mainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.orgsList.layoutManager = layoutManager

        viewModel.getOrgs()

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getOrgs()
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.orgsResponse.observe(mainActivity) { result->
            orgsList = result.data
            orgsAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

}