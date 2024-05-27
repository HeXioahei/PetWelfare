package com.example.petwelfare.ui.main.otheruser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentOtherUserArticlesBinding
import com.example.petwelfare.databinding.ItemViewpagerBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineViewModel


class OtherUserArticlesFragment(private val userId: Long) : Fragment() {

    private lateinit var binding: FragmentOtherUserArticlesBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherUserArticlesBinding.inflate(inflater, container, false)

        val viewModel : OtherUserDetailViewModel by viewModels()

        var mmyArticlesList: MutableList<Article> = mutableListOf(
            Article(), Article(),
            Article(),
            Article()
        )
        val otherUserActivity = ActivityCollector.otherUserActivity


        // 获取列表
        viewModel.getMmyArticles(userId)

        val mmyArticlesAdapter = ArticlesAdapter(mmyArticlesList)
        binding.mmyArticles.adapter = mmyArticlesAdapter
        val layoutManager = LinearLayoutManager(otherUserActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.mmyArticles.layoutManager = layoutManager

        viewModel.mmyArticles.observe(otherUserActivity) { result->
            mmyArticlesList = result.data
            mmyArticlesAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

}