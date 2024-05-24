package com.example.petwelfare.ui.main.mine.item.mine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
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

        var myArticlesList: MutableList<Article> = mutableListOf(Article(), Article(),Article(),Article())
        val mineActivity = ActivityCollector.mineActivity


        // 获取列表
        viewModel.getMyArticles(userId)

        val myArticlesAdapter = ArticlesAdapter(myArticlesList, mineActivity)
        binding.myArticles.adapter = myArticlesAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myArticles.layoutManager = layoutManager

        viewModel.myArticles.observe(mineActivity) { result->
            myArticlesList = result.data
            myArticlesAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}