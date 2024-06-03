package com.example.petwelfare.ui.main.otheruser

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
import com.example.petwelfare.databinding.FragmentOtherUserArticlesBinding
import com.example.petwelfare.databinding.FragmentOtherUserLossBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.main.mine.item.mine.MyLossFragment


class OtherUserLossFragment(val userId: Long) : Fragment() {

//    private lateinit var binding: FragmentOtherUserLossBinding
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentOtherUserLossBinding.inflate(inflater, container, false)
//
//        val viewModel : OtherUserDetailViewModel by viewModels()
//
//        var mmyLossList: MutableList<Loss> = mutableListOf(
//            Loss(),
//            Loss(),
//            Loss()
//        )
//        val otherUserActivity = ActivityCollector.otherUserActivity
//
//        // 获取列表
//        viewModel.getMmyLoss(userId)
//
//        val mmyLossAdapter = LossAdapter(mmyLossList)
//        binding.recyclerView.adapter = mmyLossAdapter
//        val layoutManager = LinearLayoutManager(otherUserActivity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.recyclerView.layoutManager = layoutManager
//
//        viewModel.mmyLoss.observe(otherUserActivity) { result->
//            mmyLossList = result.data
//            mmyLossAdapter.notifyDataSetChanged()
//        }
//
//        return binding.root
//    }


}