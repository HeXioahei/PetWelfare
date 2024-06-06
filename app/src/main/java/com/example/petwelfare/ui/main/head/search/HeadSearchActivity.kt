package com.example.petwelfare.ui.main.head.search

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ActivityHeadSearchBinding
import com.example.petwelfare.ui.BlankFragment
import com.example.petwelfare.ui.adapter.navadapter.HeadNavAdapter
import com.example.petwelfare.ui.adapter.navadapter.HeadSearchNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.CommonFragmentStateAdapter
import com.example.petwelfare.ui.main.discovery.DiscoveryFragment

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

        ActivityCollector.addActivity(this)

        binding.returnBtn.setOnClickListener {
            finish()
        }

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

                val navAdapter = HeadSearchNavAdapter(navItemList, binding.viewPager)
                binding.recyclerView.adapter = navAdapter
                val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
                layoutManager.orientation = LinearLayoutManager.HORIZONTAL
                binding.recyclerView.layoutManager = layoutManager

                val viewpagerAdapter = CommonFragmentStateAdapter(this, viewPagerList)
                binding.viewPager.adapter = viewpagerAdapter

                // ViewPager2 的 item 变化，导航栏跟着变化，导航栏的光标也跟着变化
                binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onPageSelected(position: Int) {
                        viewPagerCurrentPosition = position
                        binding.recyclerView.scrollToPosition(position)
                        navAdapter.notifyDataSetChanged()
                    }
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

}