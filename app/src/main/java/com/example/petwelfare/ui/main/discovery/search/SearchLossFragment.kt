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
import com.example.petwelfare.databinding.FragmentSearchSquareBinding
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.main.head.search.SearchSquareViewModel

class SearchLossFragment(val keywords: String) : Fragment() {

    private lateinit var binding : FragmentSearchLossBinding

    val viewModel : SearchLossViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchLossBinding.inflate(inflater, container, false)

        viewModel.searchLoss(keywords)

        val lossAdapter = LossAdapter(viewModel.searchLossList, "other")
        binding.list.adapter = lossAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.list.layoutManager = layoutManager

        viewModel.searchLossResponse.observe(this.viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            Log.d("searchLossResponse", result.toString())
            viewModel.searchLossList.clear()
            viewModel.searchLossList.addAll(result.data)
            lossAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.INVISIBLE
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.searchLoss(keywords)
    }
}