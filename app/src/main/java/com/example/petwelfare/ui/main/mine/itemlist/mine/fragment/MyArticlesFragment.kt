package com.example.petwelfare.ui.main.mine.itemlist.mine.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentMyArticlesBinding
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.main.mine.itemlist.mine.viewmodel.ItemMineViewModel


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

        val myArticlesAdapter = ArticlesAdapter(viewModel.myArticlesList, "me")
        binding.myArticles.adapter = myArticlesAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myArticles.layoutManager = layoutManager

        // 响应获取推文
        viewModel.myArticles.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            Log.d("getMyArticles2", "getMyArticles2")
            viewModel.myArticlesList.clear()
            viewModel.myArticlesList.addAll(result.data)
            myArticlesAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        // 响应删除推文
        ItemMineViewModel.delMyArticle.observe(viewLifecycleOwner) {
            Toast.makeText(PetWelfareApplication.context, "成功删除", Toast.LENGTH_SHORT).show()
            viewModel.getMyArticles(userId)
        }

        return binding.root
    }
}