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
import com.example.petwelfare.databinding.FragmentOtherUserStrayBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter
import com.example.petwelfare.ui.main.mine.item.mine.MyStrayFragment


class OtherUserStrayFragment(val userId: Long) : MyStrayFragment(userId) {


//    private lateinit var binding: FragmentOtherUserStrayBinding
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentOtherUserStrayBinding.inflate(inflater, container, false)
//
//        val viewModel : OtherUserDetailViewModel by viewModels()
//
//        var mmyStrayList: MutableList<Stray> = mutableListOf(
//            Stray(),
//            Stray(),
//            Stray()
//        )
//        val otherUserActivity = ActivityCollector.otherUserActivity
//
//
//        // 获取列表
//        viewModel.getMmyStray(userId)
//
//        val mmyArticlesAdapter = StrayAdapter(mmyStrayList)
//        binding.recyclerView.adapter = mmyArticlesAdapter
//        val layoutManager = LinearLayoutManager(otherUserActivity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.recyclerView.layoutManager = layoutManager
//
//        viewModel.mmyStray.observe(otherUserActivity) { result->
//            mmyStrayList = result.data
//            mmyArticlesAdapter.notifyDataSetChanged()
//        }
//
//        return binding.root
//    }
}