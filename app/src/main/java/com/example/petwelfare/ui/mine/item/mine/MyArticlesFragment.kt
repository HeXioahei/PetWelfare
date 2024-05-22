package com.example.petwelfare.ui.mine.item.mine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentMyArticlesBinding
import com.example.petwelfare.databinding.FragmentMyStrayBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.listadapter.ArticlesAdapter
import kotlin.math.min


class MyArticlesFragment : Fragment() {

    private lateinit var binding : FragmentMyArticlesBinding
    private var myArticlesList: MutableList<Article> = mutableListOf(Article(), Article(),Article(),Article())
    private val mineActivity = ActivityCollector.mineActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyArticlesBinding.inflate(inflater, container,false)

        val myArticlesAdapter = ArticlesAdapter(myArticlesList, mineActivity)
        binding.myArticles.adapter = myArticlesAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myArticles.layoutManager = layoutManager

        return binding.root
    }
}