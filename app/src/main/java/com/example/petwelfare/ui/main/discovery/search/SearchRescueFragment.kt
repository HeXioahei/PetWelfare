package com.example.petwelfare.ui.main.discovery.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentSearchLossBinding
import com.example.petwelfare.databinding.FragmentSearchRescueBinding
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.adapter.listadapter.OrgsAdapter

class SearchRescueFragment(val keywords : String) : Fragment() {

    private lateinit var binding : FragmentSearchRescueBinding

    val viewModel : SearchRescueViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchRescueBinding.inflate(inflater, container, false)

        viewModel.searchOrgs(keywords)

        val orgAdapter = OrgsAdapter(viewModel.searchOrgsList)
        binding.list.adapter = orgAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.list.layoutManager = layoutManager

        viewModel.searchOrgsResponse.observe(this.viewLifecycleOwner) { result->
            Log.d("searchLossResponse", result.toString())
            viewModel.searchOrgsList.clear()
            viewModel.searchOrgsList.addAll(result.data.org_list)
            orgAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.INVISIBLE
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchOrgs(keywords)
    }
}