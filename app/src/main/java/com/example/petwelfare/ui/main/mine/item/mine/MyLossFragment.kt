package com.example.petwelfare.ui.main.mine.item.mine

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
import com.example.petwelfare.databinding.FragmentMyArticlesBinding
import com.example.petwelfare.databinding.FragmentMyLossBinding
import com.example.petwelfare.databinding.FragmentMyStrayBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter


class MyLossFragment : Fragment() {

    private lateinit var binding : FragmentMyLossBinding

    private var myLossList: MutableList<Loss> = mutableListOf(Loss(), Loss(), Loss(), Loss())
    private val mineActivity = ActivityCollector.mineActivity


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyLossBinding.inflate(inflater, container,false)

        val viewModel : ItemMineViewModel by viewModels()

        // 获取列表
        viewModel.getMyLoss()

        val myLossAdapter = LossAdapter(myLossList, mineActivity)
        binding.myLoss.adapter = myLossAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myLoss.layoutManager = layoutManager

        viewModel.myLoss.observe(mineActivity) { result->
            myLossList = result.data
            myLossAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}