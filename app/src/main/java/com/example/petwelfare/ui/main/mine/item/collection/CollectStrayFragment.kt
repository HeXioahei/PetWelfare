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
import com.example.petwelfare.databinding.FragmentCollectStrayBinding
import com.example.petwelfare.databinding.FragmentMyStrayBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter


class CollectStrayFragment : Fragment() {

    private lateinit var binding : FragmentCollectStrayBinding
    private var collectStrayList: MutableList<Stray> = mutableListOf(Stray(), Stray(), Stray(), Stray())
    private val mineActivity = ActivityCollector.mineActivity


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectStrayBinding.inflate(inflater, container,false)

        val viewModel : ItemCollectionViewModel by viewModels()

        // 获取列表
        //viewModel.getCollectStary()

        val collectStrayAdapter = StrayAdapter(collectStrayList)
        binding.collectStray.adapter = collectStrayAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.collectStray.layoutManager = layoutManager

        viewModel.collectStray.observe(mineActivity) { result->
            collectStrayList = result.data
            collectStrayAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}