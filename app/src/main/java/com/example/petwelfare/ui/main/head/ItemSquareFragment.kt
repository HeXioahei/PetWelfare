package com.example.petwelfare.ui.main.head

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.FragmentItemSquareBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter

class ItemSquareFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    private lateinit var binding: FragmentItemSquareBinding
    private var articlesList: MutableList<Article> = mutableListOf(Article(), Article(),Article(), Article())

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentItemSquareBinding.inflate(inflater, container, false)

        val viewModel: ItemSquareViewModel by viewModels()

        val articlesAdapter = ArticlesAdapter(articlesList, mainActivity)
        binding.articlesList.adapter = articlesAdapter
        val layoutManager = LinearLayoutManager(mainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.articlesList.layoutManager = layoutManager

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getArticles()
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.articlesResponse.observe(mainActivity) { result->
            Log.d("articlesResponse", "articlesResponse")
            if (result == null) {
                binding.swipeRefresh.isRefreshing = false
            }
            articlesList.clear()
            articlesList = result.data
            articlesAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

}