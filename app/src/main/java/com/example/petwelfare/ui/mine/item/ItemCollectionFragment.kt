package com.example.petwelfare.ui.mine.item

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentItemCollectionBinding
import com.example.petwelfare.ui.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.listadapter.LossAdapter
import com.example.petwelfare.ui.listadapter.OrgsAdapter
import com.example.petwelfare.ui.listadapter.StrayAdapter
import com.example.petwelfare.ui.mine.MineActivity

class ItemCollectionFragment : Fragment() {

    private lateinit var binding : FragmentItemCollectionBinding
    private lateinit var navList: MutableList<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemCollectionBinding.inflate(inflater, container, false)

        val activity = activity as MineActivity

        val viewModel: ItemCollectionViewModel by viewModels()

        navList = mutableListOf(binding.article, binding.loss, binding.stray, binding.orgs)

        viewModel.collectArticlesData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.collectArticles = list
            }
        }

        viewModel.collectLossData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.collectLoss = list
            }
        }

        viewModel.collectStrayData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.collectStray = list
            }
        }

        viewModel.collectOrgData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.collectOrg = list
            }
        }

        val layoutInflater = LinearLayoutManager(activity)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL
        val collectArticleAdapter0 = ArticlesAdapter(viewModel.collectArticles, activity)
        binding.collectionRecyclerView.adapter = collectArticleAdapter0
        binding.collectionRecyclerView.layoutManager = layoutInflater



        binding.article.setOnClickListener {
            cursorMove(binding.article)
            val collectArticleAdapter = ArticlesAdapter(viewModel.collectArticles, activity)
            binding.collectionRecyclerView.adapter = collectArticleAdapter
            binding.collectionRecyclerView.layoutManager = layoutInflater
        }

        binding.loss.setOnClickListener {
            cursorMove(binding.loss)
            val collectLossAdapter = LossAdapter(viewModel.collectLoss, activity)
            binding.collectionRecyclerView.adapter = collectLossAdapter
            binding.collectionRecyclerView.layoutManager = layoutInflater
        }

        binding.stray.setOnClickListener {
            cursorMove(binding.stray)
            val collectStrayAdapter = StrayAdapter(viewModel.collectStray, activity)
            binding.collectionRecyclerView.adapter = collectStrayAdapter
            binding.collectionRecyclerView.layoutManager = layoutInflater
        }

        binding.orgs.setOnClickListener {
            cursorMove(binding.orgs)
            val collectOrgAdapter = OrgsAdapter(viewModel.collectOrg, activity)
            binding.collectionRecyclerView.adapter = collectOrgAdapter
            binding.collectionRecyclerView.layoutManager = layoutInflater
        }

        return binding.root
    }

    private fun cursorMove(view: View) {
        for (view2 in navList) {
            if (view2.background.isVisible) {
                view2.background.setVisible(false, true)
            }
        }
        view.background.setVisible(true, true)
    }
}