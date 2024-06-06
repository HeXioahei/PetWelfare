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
import com.example.petwelfare.databinding.FragmentSearchStrayBinding
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter


class SearchStrayFragment(val keywords: String) : Fragment() {

    private lateinit var binding : FragmentSearchStrayBinding

    val viewModel : SearchStrayViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchStrayBinding.inflate(inflater, container, false)

        viewModel.searchStray(keywords)

        val strayAdapter = StrayAdapter(viewModel.searchStrayList)
        binding.list.adapter = strayAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.list.layoutManager = layoutManager

        viewModel.searchStrayResponse.observe(this.viewLifecycleOwner) { result->
            if (result.data.stray_list.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            Log.d("searchLossResponse", result.toString())
            viewModel.searchStrayList.clear()
            viewModel.searchStrayList.addAll(result.data.stray_list)
            strayAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.INVISIBLE
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchStray(keywords)
    }

}