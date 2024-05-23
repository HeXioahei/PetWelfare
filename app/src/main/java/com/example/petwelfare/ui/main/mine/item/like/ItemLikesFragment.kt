package com.example.petwelfare.ui.main.mine.item.like

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.databinding.FragmentItemLikesBinding
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.main.mine.MineViewModel

class ItemLikesFragment : Fragment() {


    private lateinit var binding : FragmentItemLikesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemLikesBinding.inflate(inflater, container, false)

        val activity = activity as com.example.petwelfare.ui.main.mine.MineActivity

        val viewModel: ItemLikesViewModel by viewModels()
        val viewModel2: MineViewModel by viewModels()

        /**
         *
         * 点赞还不一定生成列表，先放着
         *
         */


//        viewModel.setId(viewModel2.myDetail.id)
//        viewModel.likesArticleData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.likesArticle = list
//            }
//        }

        val layoutInflater = LinearLayoutManager(activity)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL

        val likesAdapter = ArticlesAdapter(viewModel.likesArticle, activity)
        binding.likesRecyclerView.adapter = likesAdapter
        binding.likesRecyclerView.layoutManager = layoutInflater

        return binding.root
    }

}