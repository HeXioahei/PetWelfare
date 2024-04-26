package com.example.petwelfare.ui.mine.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentItemMineBinding
import com.example.petwelfare.ui.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.listadapter.LossAdapter
import com.example.petwelfare.ui.listadapter.StrayAdapter
import com.example.petwelfare.ui.mine.MineActivity
import com.example.petwelfare.ui.mine.MineViewModel

class ItemMineFragment : Fragment() {

    private lateinit var binding : FragmentItemMineBinding
    private lateinit var navList: MutableList<View>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemMineBinding.inflate(inflater, container, false)

        navList = mutableListOf(binding.article, binding.loss, binding.stray)

        val activity = activity as MineActivity

        val viewModel: ItemMineViewModel by viewModels()
        val viewModel2: MineViewModel by viewModels()

        viewModel.setId(viewModel2.myDetail.id)

        viewModel.myArticlesData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.myArticles = list
            }
        }

        viewModel.myLossData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.myLoss = list
            }
        }

        viewModel.myStrayData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.myStray = list
            }
        }

        val layoutInflater = LinearLayoutManager(activity)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL

        binding.article.setOnClickListener {
            cursorMove(binding.article)
            val myArticleAdapter = ArticlesAdapter(viewModel.myArticles, activity)
            binding.itemMineRecyclerView.adapter = myArticleAdapter
            binding.itemMineRecyclerView.layoutManager = layoutInflater
        }

        binding.loss.setOnClickListener {
            cursorMove(binding.loss)
            val myLossAdapter = LossAdapter(viewModel.myLoss, activity)
            binding.itemMineRecyclerView.adapter = myLossAdapter
            binding.itemMineRecyclerView.layoutManager = layoutInflater
        }

        binding.stray.setOnClickListener {
            cursorMove(binding.stray)
            val myStrayAdapter = StrayAdapter(viewModel.myStray, activity)
            binding.itemMineRecyclerView.adapter = myStrayAdapter
            binding.itemMineRecyclerView.layoutManager = layoutInflater
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