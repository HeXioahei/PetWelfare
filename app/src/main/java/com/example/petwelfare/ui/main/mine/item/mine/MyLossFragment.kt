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
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentMyArticlesBinding
import com.example.petwelfare.databinding.FragmentMyLossBinding
import com.example.petwelfare.databinding.FragmentMyStrayBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter


open class MyLossFragment(private val userId: Long) : Fragment() {

    private lateinit var binding : FragmentMyLossBinding

//    private var myLossList: MutableList<Loss> = mutableListOf(Loss(), Loss(), Loss(), Loss())
    private val mineActivity = ActivityCollector.mineActivity


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyLossBinding.inflate(inflater, container,false)

        val viewModel : ItemMineViewModel by viewModels()

        // 获取列表
        binding.swipeRefresh.isRefreshing = true
        viewModel.getMyLoss(userId)
        viewModel.delayAction {
            binding.swipeRefresh.isRefreshing = false
        }

        // 刷新
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMyLoss(userId)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        val myLossAdapter = LossAdapter(viewModel.myLossList)
        binding.myLoss.adapter = myLossAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myLoss.layoutManager = layoutManager

        viewModel.myLoss.observe(viewLifecycleOwner) { result->
            Log.d("myLoss2", "myLoss2")
            viewModel.myLossList.clear()
            viewModel.myLossList.addAll(result.data)
            myLossAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }
}