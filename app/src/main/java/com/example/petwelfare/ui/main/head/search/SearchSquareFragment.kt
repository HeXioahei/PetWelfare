package com.example.petwelfare.ui.main.head.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentSearchSquareBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.main.head.ItemSquareFragment

class SearchSquareFragment(private val keywords: String, val activity: HeadSearchActivity) : Fragment() {

    private lateinit var binding : FragmentSearchSquareBinding
    private var searchArticlesList : MutableList<Article> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchSquareBinding.inflate(inflater, container, false)

        val viewModel : SearchSquareViewModel by viewModels()

        viewModel.searchArticles(keywords)

        viewModel.searchArticlesResponse.observe(activity) { result->
            Log.d("searchArticlesResponse", result.toString())
            searchArticlesList = result.data
            binding.progressBar.visibility = View.INVISIBLE
        }

        val articlesAdapter = ArticlesAdapter(searchArticlesList, activity)
        binding.articlesList.adapter = articlesAdapter
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.articlesList.layoutManager = layoutManager

        return binding.root
    }
}