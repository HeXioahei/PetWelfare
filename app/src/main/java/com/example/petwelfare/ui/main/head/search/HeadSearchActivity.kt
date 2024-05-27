package com.example.petwelfare.ui.main.head.search

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ActivityHeadSearchBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.BlankFragment
import com.example.petwelfare.ui.adapter.navadapter.HeadNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.CommonFragmentStateAdapter

class HeadSearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHeadSearchBinding

    private val viewModel : HeadSearchViewModel by viewModels()
    private val navItemList = listOf("广场", "关注", "社区", "讨论", "附近")

    companion object {
        var viewPagerCurrentPosition = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.beginSearch.setOnClickListener {
            val keywords = binding.searchBar.text.toString()
            if (keywords != "") {
                val viewPagerList = mutableListOf(
                    SearchSquareFragment(keywords),
                    BlankFragment(),
                    BlankFragment(),
                    BlankFragment(),
                    BlankFragment()
                )

                val navAdapter = HeadNavAdapter(navItemList, binding.viewPager)
                binding.recyclerView.adapter = navAdapter
                val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.recyclerView.layoutManager = layoutManager

                val viewpagerAdapter = CommonFragmentStateAdapter(this, viewPagerList)
                binding.viewPager.adapter = viewpagerAdapter
            }
        }
    }

}