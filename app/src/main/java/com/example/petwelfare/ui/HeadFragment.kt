package com.example.petwelfare.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentHeadBinding
import com.example.petwelfare.ui.head.ItemDiscussFragment
import com.example.petwelfare.ui.head.ItemFollowFragment
import com.example.petwelfare.ui.head.ItemNearFragment
import com.example.petwelfare.ui.head.ItemCommunityFragment
import com.example.petwelfare.ui.head.ItemSquareFragment
import com.example.petwelfare.ui.navadapter.HeadNavAdapter
import com.example.petwelfare.ui.viewpageradapter.HeadViewPagerAdapter
import java.util.Collections.list

class HeadFragment : Fragment() {

    private lateinit var binding : FragmentHeadBinding

//    // 获取FragmentManager
    private lateinit var myChildFragmentManager : FragmentManager
//    // 创建Fragment实例
//    private val itemSquareFragment = ItemSquareFragment(ActivityCollector.mainActivity)
//    private val itemCommunityFragment = ItemCommunityFragment(ActivityCollector.mainActivity)
//    private val itemNearFragment = ItemNearFragment(ActivityCollector.mainActivity)
//    private val itemFollowFragment = ItemFollowFragment(ActivityCollector.mainActivity)
//    private val itemDiscussFragment = ItemDiscussFragment(ActivityCollector.mainActivity)

//    private var navItemList : List<AppCompatTextView> = listOf<AppCompatTextView>()

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

//        navItemList = listOf(
//            binding.toSquareBtn,
//            binding.toCommunityBtn,
//            binding.toFollowBtn,
//            binding.toDiscussBtn,
//            binding.toNearBtn
//        )



        myChildFragmentManager = childFragmentManager  // childFragmentManager需要在onCreateView()中获取
//
//        replaceFragment("square")
//
//        binding.toSquareBtn.setOnClickListener {
//            replaceFragment("square")
//        }
//        binding.toCommunityBtn.setOnClickListener {
//            replaceFragment("community")
//        }
//        binding.toFollowBtn.setOnClickListener {
//            replaceFragment("follow")
//        }
//        binding.toDiscussBtn.setOnClickListener {
//            replaceFragment("discuss")
//        }
//        binding.toNearBtn.setOnClickListener {
//            replaceFragment("near")
//        }

        val navAdapter = HeadNavAdapter(navItemList, binding.viewPager)
        binding.navBar.adapter = navAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.navBar.layoutManager = layoutManager

        val viewpagerAdapter = HeadViewPagerAdapter(viewPagerList, childFragmentManager)
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

//    // 更换fragment
//    private fun replaceFragment(type: String) {
//        // 创建FragmentTransaction实例
//        val transaction: FragmentTransaction = myChildFragmentManager.beginTransaction()
//        // 添加fragment
//        when (type) {
//            "square" -> {
//                transaction.replace(R.id.fragmentHead, itemSquareFragment)
//            }
//            "community" -> {
//                transaction.replace(R.id.fragmentHead, itemCommunityFragment)
//            }
//            "follow" -> {
//                transaction.replace(R.id.fragmentHead, itemFollowFragment)
//            }
//            "discuss" -> {
//                transaction.replace(R.id.fragmentHead, itemDiscussFragment)
//            }
//            "near" -> {
//                transaction.replace(R.id.fragmentHead, itemNearFragment)
//            }
//        }
//        // 提交事务
//        transaction.commit()
//    }
//
//    private fun navItemActive(itemActive: AppCompatTextView) {
//        for (item in navItemList) {
//            if (item == itemActive) {
//
//            }
//        }
//    }
}