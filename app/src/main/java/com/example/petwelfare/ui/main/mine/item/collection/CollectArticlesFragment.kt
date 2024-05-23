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
import com.example.petwelfare.databinding.FragmentCollectArticlesBinding
import com.example.petwelfare.databinding.FragmentMyArticlesBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter


class CollectArticlesFragment : Fragment() {

    private lateinit var binding : FragmentCollectArticlesBinding
    private var collectArticlesList: MutableList<Article> = mutableListOf(Article(), Article(),Article(),Article())
    private val mineActivity = ActivityCollector.mineActivity

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCollectArticlesBinding.inflate(inflater, container,false)

        val viewModel : ItemCollectionViewModel by viewModels()

        // 获取列表
        //viewModel.getCollectArticles()

        val collectArticlesAdapter = ArticlesAdapter(collectArticlesList, mineActivity)
        binding.collectArticles.adapter = collectArticlesAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.collectArticles.layoutManager = layoutManager

        viewModel.collectArticles.observe(mineActivity) { result->
            collectArticlesList = result.data
            collectArticlesAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}