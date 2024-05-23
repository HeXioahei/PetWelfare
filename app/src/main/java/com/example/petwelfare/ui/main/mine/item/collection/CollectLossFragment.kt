package com.example.petwelfare.ui.main.mine.item.collection

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.FragmentCollectLossBinding
import com.example.petwelfare.databinding.FragmentMyLossBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter


class CollectLossFragment : Fragment() {

    private lateinit var binding : FragmentCollectLossBinding

    private var collectLossList: MutableList<Loss> = mutableListOf(Loss(), Loss(), Loss(), Loss())
    private val mineActivity = ActivityCollector.mineActivity


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectLossBinding.inflate(inflater, container,false)

        val viewModel : ItemCollectionViewModel by viewModels()

        // 获取列表
        //viewModel.getCollectLoss()

        val collectLossAdapter = LossAdapter(collectLossList, mineActivity)
        binding.collectLoss.adapter = collectLossAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.collectLoss.layoutManager = layoutManager

        viewModel.collectLoss.observe(mineActivity) { result->
            collectLossList = result.data
            collectLossAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}