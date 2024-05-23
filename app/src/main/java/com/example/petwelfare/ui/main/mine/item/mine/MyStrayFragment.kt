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
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentMyStrayBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter


class MyStrayFragment : Fragment() {

    private lateinit var binding : FragmentMyStrayBinding
    private var myStrayList: MutableList<Stray> = mutableListOf(Stray(), Stray(), Stray(), Stray())
    private val mineActivity = ActivityCollector.mineActivity


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyStrayBinding.inflate(inflater, container,false)

        val viewModel : ItemMineViewModel by viewModels()

        // 获取列表
        viewModel.getMyStray()

        val myStrayAdapter = StrayAdapter(myStrayList, mineActivity)
        binding.myStray.adapter = myStrayAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.myStray.layoutManager = layoutManager

        viewModel.myStray.observe(mineActivity) { result->
            myStrayList = result.data
            myStrayAdapter.notifyDataSetChanged()
        }

        return binding.root
    }
}