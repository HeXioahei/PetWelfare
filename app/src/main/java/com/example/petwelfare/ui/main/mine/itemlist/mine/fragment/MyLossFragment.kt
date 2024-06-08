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
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentMyLossBinding
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.main.mine.itemlist.mine.viewmodel.ItemMineViewModel


open class MyLossFragment(private val userId: Long) : Fragment() {

    private lateinit var binding : FragmentMyLossBinding


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

        val myLossAdapter = LossAdapter(viewModel.myLossList, "me")
        binding.myLoss.adapter = myLossAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myLoss.layoutManager = layoutManager

        viewModel.myLoss.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            Log.d("myLoss2", "myLoss2")
            viewModel.myLossList.clear()
            viewModel.myLossList.addAll(result.data)
            myLossAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        // 响应删除走失
        ItemMineViewModel.delMyLoss.observe(viewLifecycleOwner) {
            Toast.makeText(PetWelfareApplication.context, "成功删除", Toast.LENGTH_SHORT).show()
            viewModel.getMyLoss(userId)
        }

        return binding.root
    }
}