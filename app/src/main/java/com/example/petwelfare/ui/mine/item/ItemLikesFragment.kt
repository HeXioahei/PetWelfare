package com.example.petwelfare.ui.mine.item

import android.os.Binder
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentItemLikesBinding
import com.example.petwelfare.ui.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.listadapter.LossAdapter
import com.example.petwelfare.ui.listadapter.StrayAdapter
import com.example.petwelfare.ui.mine.MineActivity
import com.example.petwelfare.ui.mine.MineViewModel

class ItemLikesFragment : Fragment() {

    private lateinit var viewModel: ItemLikesViewModel
    private lateinit var viewModel2: MineViewModel
    private lateinit var binding : FragmentItemLikesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemLikesBinding.inflate(inflater, container, false)

        val activity = activity as MineActivity

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