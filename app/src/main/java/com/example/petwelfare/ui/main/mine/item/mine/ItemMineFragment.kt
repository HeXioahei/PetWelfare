package com.example.petwelfare.ui.main.mine.item.mine

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentItemMineBinding
import com.example.petwelfare.ui.adapter.navadapter.MineNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.MineFragmentStateAdapter

class ItemMineFragment : Fragment() {

    private lateinit var binding : FragmentItemMineBinding
    private val viewModel : ItemMineViewModel by viewModels()

    companion object {
        var viewPagerCurrentPosition = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemMineBinding.inflate(inflater, container, false)

        val navItemList = listOf("日常分享", "走失动物", "流浪动物", "收养动物")

        val navAdapter = MineNavAdapter(navItemList, binding.viewPager)
        binding.navBar.adapter = navAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.navBar.layoutManager = layoutManager

        val viewpagerAdapter = MineFragmentStateAdapter(this, viewModel.viewPagerList)
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

//    private fun cursorMove(view: View) {
//        for (view2 in navList) {
//            if (view2.background.isVisible) {
//                view2.background.setVisible(false, true)
//            }
//        }
//        view.background.setVisible(true, true)
//    }

}