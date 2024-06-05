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
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentMyStrayBinding
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter


open class MyStrayFragment(private val userId: Long) : Fragment() {

    private lateinit var binding : FragmentMyStrayBinding
//    private var myStrayList: MutableList<Stray> = mutableListOf(Stray(), Stray(), Stray(), Stray())
    private val mineActivity = ActivityCollector.mineActivity


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyStrayBinding.inflate(inflater, container,false)

        val viewModel : ItemMineViewModel by viewModels()

        // 获取列表
        binding.swipeRefresh.isRefreshing = true
        viewModel.getMyStray(userId)
        viewModel.delayAction {
            binding.swipeRefresh.isRefreshing = false
        }

        // 刷新
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMyStray(userId)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        val myStrayAdapter = StrayAdapter(viewModel.myStrayList)
        binding.myStray.adapter = myStrayAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myStray.layoutManager = layoutManager

        viewModel.myStray.observe(viewLifecycleOwner) { result->
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            Log.d("myStray2", "myStray2")
            viewModel.myStrayList.clear()
            viewModel.myStrayList.addAll(result.data)
            myStrayAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }
}