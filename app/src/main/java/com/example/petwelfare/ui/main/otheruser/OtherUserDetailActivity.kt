package com.example.petwelfare.ui.main.otheruser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.ActivityOtherUserDetailBinding
import com.example.petwelfare.ui.adapter.navadapter.MineNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.CommonFragmentStateAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.ViewPagerAdapter
import com.example.petwelfare.ui.main.mine.item.mine.MyArticlesFragment
import com.example.petwelfare.ui.main.mine.item.mine.MyLossFragment
import com.example.petwelfare.ui.main.mine.item.mine.MyStrayFragment
import com.example.petwelfare.ui.main.mine.item.pet.ItemPetFragment

class OtherUserDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOtherUserDetailBinding

    private val navItemList = listOf("日常分享", "走失动物", "流浪动物", "宠物屋")

    companion object {
        var viewPagerCurrentPosition = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtherUserDetailBinding.inflate(layoutInflater)

        ActivityCollector.addActivity(this)
        ActivityCollector.otherUserActivity = this

        setContentView(binding.root)

        val userId = intent.getLongExtra("userId", -1)

        val viewPagerList: List<Fragment> = listOf(MyArticlesFragment(userId), MyLossFragment(userId), MyStrayFragment(userId), ItemPetFragment(userId))

        val navAdapter = MineNavAdapter(navItemList, binding.viewPager)
        binding.recyclerView.adapter = navAdapter
        val layoutManager = LinearLayoutManager(this)
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

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}