package com.example.petwelfare.ui.main.mine.item.collection

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentCollectLossBinding
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter


class CollectLossFragment : Fragment() {

    private lateinit var binding : FragmentCollectLossBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectLossBinding.inflate(inflater, container,false)

        val viewModel : ItemCollectionViewModel by viewModels()

        // 获取列表
        viewModel.getCollectLoss()

        val collectLossAdapter = LossAdapter(viewModel.collectLossList)
        binding.collectLoss.adapter = collectLossAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.collectLoss.layoutManager = layoutManager

        viewModel.collectLoss.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.collectLossList.clear()
            viewModel.collectLossList.addAll(result.data)
            collectLossAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}