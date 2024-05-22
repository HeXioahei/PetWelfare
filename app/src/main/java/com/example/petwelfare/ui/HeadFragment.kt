package com.example.petwelfare.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentHeadBinding
import com.example.petwelfare.ui.head.ItemDiscussFragment
import com.example.petwelfare.ui.head.ItemFollowFragment
import com.example.petwelfare.ui.head.ItemNearFragment
import com.example.petwelfare.ui.head.ItemCommunityFragment
import com.example.petwelfare.ui.head.ItemSquareFragment
import com.example.petwelfare.ui.navadapter.HeadNavAdapter
import com.example.petwelfare.ui.viewpageradapter.HeadFragmentStateAdapter

class HeadFragment : Fragment() {

    private lateinit var binding : FragmentHeadBinding

    private val navItemList = listOf("广场", "关注", "社区", "讨论", "附近")
    private val viewPagerList: List<Fragment> = listOf(
        ItemSquareFragment(ActivityCollector.mainActivity),
        ItemFollowFragment(ActivityCollector.mainActivity),
        ItemCommunityFragment(ActivityCollector.mainActivity),
        ItemDiscussFragment(ActivityCollector.mainActivity),
        ItemNearFragment(ActivityCollector.mainActivity)
    )

    companion object {
        var viewPagerCurrentPosition = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeadBinding.inflate(inflater, container, false)

        val navAdapter = HeadNavAdapter(navItemList, binding.viewPager)
        binding.navBar.adapter = navAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.navBar.layoutManager = layoutManager

        val viewpagerAdapter = HeadFragmentStateAdapter(this, viewPagerList)
        binding.viewPager.adapter = viewpagerAdapter

        // ViewPager2 的 item 变化，导航栏跟着变化，导航栏的光标也跟着变化
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onPageSelected(position: Int) {
                viewPagerCurrentPosition = position
                binding.navBar.scrollToPosition(position)
                navAdapter.notifyDataSetChanged()
            }
        })

        return binding.root
    }
}