package com.example.petwelfare.ui.main.mine.itemlist.collection.fragment

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
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.main.mine.itemlist.collection.viewmodel.ItemCollectionViewModel


class CollectArticlesFragment : Fragment() {

    private lateinit var binding : FragmentCollectArticlesBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectArticlesBinding.inflate(inflater, container,false)

        val viewModel : ItemCollectionViewModel by viewModels()

        // 获取列表
        viewModel.getCollectArticles()

        val collectArticlesAdapter = ArticlesAdapter(viewModel.collectArticlesList, "other")
        binding.collectArticles.adapter = collectArticlesAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.collectArticles.layoutManager = layoutManager

        viewModel.collectArticles.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.collectArticlesList.clear()
            viewModel.collectArticlesList.addAll(result.data)
            collectArticlesAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}