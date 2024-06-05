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
import com.example.petwelfare.databinding.FragmentCollectStrayBinding
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter


class CollectStrayFragment : Fragment() {

    private lateinit var binding : FragmentCollectStrayBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectStrayBinding.inflate(inflater, container,false)

        val viewModel : ItemCollectionViewModel by viewModels()

        // 获取列表
        viewModel.getCollectStray()

        val collectStrayAdapter = StrayAdapter(viewModel.collectStrayList)
        binding.collectStray.adapter = collectStrayAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.collectStray.layoutManager = layoutManager

        viewModel.collectStray.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.collectStrayList.clear()
            viewModel.collectStrayList.addAll(result.data)
            collectStrayAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}