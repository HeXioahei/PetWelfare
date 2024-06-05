package com.example.petwelfare.ui.main.mine.item.like

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentCollectArticlesBinding
import com.example.petwelfare.databinding.FragmentItemLikesBinding
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.main.mine.MineViewModel
import com.example.petwelfare.ui.main.mine.item.collection.ItemCollectionViewModel

class ItemLikesFragment : Fragment() {


    private lateinit var binding : FragmentItemLikesBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemLikesBinding.inflate(inflater, container,false)
//
//        val viewModel : ItemLikesViewModel by viewModels()
//
//        // 获取列表
//        viewModel.getCollectArticles()
//
//        val collectArticlesAdapter = ArticlesAdapter(viewModel.collectArticlesList)
//        binding.collectArticles.adapter = collectArticlesAdapter
//        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.collectArticles.layoutManager = layoutManager
//
//        viewModel.collectArticles.observe(viewLifecycleOwner) { result->
//            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
//            else binding.image.visibility = View.VISIBLE
//            viewModel.collectArticlesList.clear()
//            viewModel.collectArticlesList.addAll(result.data)
//            collectArticlesAdapter.notifyDataSetChanged()
//        }
//
        return binding.root
    }

}