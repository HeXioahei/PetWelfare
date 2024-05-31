package com.example.petwelfare.ui.main.head.search

import android.annotation.SuppressLint
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

class SearchSquareFragment(private val keywords: String) : Fragment() {

    private lateinit var binding : FragmentSearchSquareBinding
//    private var searchArticlesList : MutableList<Article> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchSquareBinding.inflate(inflater, container, false)

        val viewModel : SearchSquareViewModel by viewModels()

        viewModel.searchArticles(keywords)

        val articlesAdapter = ArticlesAdapter(viewModel.searchArticlesList)
        binding.articlesList.adapter = articlesAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.articlesList.layoutManager = layoutManager

        viewModel.searchArticlesResponse.observe(this.viewLifecycleOwner) { result->
            Log.d("searchArticlesResponse", result.toString())
            viewModel.searchArticlesList.clear()
            viewModel.searchArticlesList.addAll(result.data)
            articlesAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.INVISIBLE
        }

        return binding.root
    }
}