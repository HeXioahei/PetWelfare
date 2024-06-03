package com.example.petwelfare.ui.main.mine.item.mine

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
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentMyArticlesBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter


open class MyArticlesFragment(private val userId: Long) : Fragment() {

    private lateinit var binding : FragmentMyArticlesBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyArticlesBinding.inflate(inflater, container,false)

        val viewModel : ItemMineViewModel by viewModels()

        // 获取列表
        binding.swipeRefresh.isRefreshing = true
        viewModel.getMyArticles(userId)
        viewModel.delayAction {
            binding.swipeRefresh.isRefreshing = false
        }

        // 刷新
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMyArticles(userId)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        val myArticlesAdapter = ArticlesAdapter(viewModel.myArticlesList)
        binding.myArticles.adapter = myArticlesAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myArticles.layoutManager = layoutManager

        viewModel.myArticles.observe(viewLifecycleOwner) { result->
            Log.d("getMyArticles2", "getMyArticles2")
            viewModel.myArticlesList.clear()
            viewModel.myArticlesList.addAll(result.data)
            myArticlesAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }
}