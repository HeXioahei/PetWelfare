package com.example.petwelfare.ui.main.head

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentHeadBinding
import com.example.petwelfare.ui.adapter.navadapter.HeadNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.HeadFragmentStateAdapter
import com.example.petwelfare.ui.main.head.search.HeadSearchActivity

class HeadFragment : Fragment() {

    private lateinit var binding : FragmentHeadBinding

    private val navItemList = listOf("广场", "关注", "社区", "讨论", "附近")
    private val viewPagerList: List<Fragment> = listOf(
        ItemSquareFragment(),
        ItemFollowFragment(),
        ItemCommunityFragment(),
        ItemDiscussFragment(),
        ItemNearFragment()
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

        // 搜索
        binding.toSearchBtn.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, HeadSearchActivity::class.java)
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            PetWelfareApplication.context.startActivity(intent)
        }

        return binding.root
    }
}