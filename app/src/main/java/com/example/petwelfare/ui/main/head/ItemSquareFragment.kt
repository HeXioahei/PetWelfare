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

open class ItemSquareFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    private lateinit var binding: FragmentItemSquareBinding
    private var articlesList: MutableList<Article> = mutableListOf(Article(), Article(),Article(), Article())

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentItemSquareBinding.inflate(inflater, container, false)

        val viewModel: ItemSquareViewModel by viewModels()

        val articlesAdapter = ArticlesAdapter(articlesList)
        binding.articlesList.adapter = articlesAdapter
        val layoutManager = LinearLayoutManager(mainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.articlesList.layoutManager = layoutManager

        var order = 1
        // 一开始先获取列表数据
        viewModel.getArticles(order)
        binding.swipeRefresh.isRefreshing = true

        // 启动刷新，进行网络请求获取列表数据
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getArticles(order)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // 响应返回的数据，进行页面显示的更新
        viewModel.articlesResponse.observe(this.viewLifecycleOwner) { result->
            Log.d("articlesResponse", "articlesResponse")
            binding.swipeRefresh.isRefreshing = false
            articlesList.clear()
            articlesList.addAll(result.data)
            Log.d("articlesList", articlesList.toString())
            articlesAdapter.notifyDataSetChanged()
            order++
        }

        return binding.root
    }

}