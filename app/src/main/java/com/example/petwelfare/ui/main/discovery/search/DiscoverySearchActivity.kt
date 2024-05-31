package com.example.petwelfare.ui.main.discovery.search

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityDiscoverySearchBinding
import com.example.petwelfare.databinding.ActivityHeadSearchBinding
import com.example.petwelfare.ui.BlankFragment
import com.example.petwelfare.ui.adapter.navadapter.HeadNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.CommonFragmentStateAdapter
import com.example.petwelfare.ui.main.head.search.HeadSearchViewModel
import com.example.petwelfare.ui.main.head.search.SearchSquareFragment

class DiscoverySearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDiscoverySearchBinding

    private val viewModel : HeadSearchViewModel by viewModels()
    private val navItemList = listOf("走失", "流浪", "寄养", "收养", "救助站")

    companion object {
        var viewPagerCurrentPosition = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiscoverySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.beginSearch.setOnClickListener {
            val keywords = binding.searchBar.text.toString()
            if (keywords != "") {
                val viewPagerList = mutableListOf(
                    SearchLossFragment(keywords),
                    SearchStrayFragment(keywords),
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

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

}