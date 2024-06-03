package com.example.petwelfare.ui.main.mine.item.collection

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentItemCollectionBinding
import com.example.petwelfare.ui.BlankFragment
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter
import com.example.petwelfare.ui.adapter.navadapter.CollectionNavAdapter
import com.example.petwelfare.ui.adapter.navadapter.MineNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.CollectionFragmentStateAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.MineFragmentStateAdapter
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineFragment
import com.example.petwelfare.ui.main.mine.item.mine.MyArticlesFragment
import com.example.petwelfare.ui.main.mine.item.mine.MyLossFragment
import com.example.petwelfare.ui.main.mine.item.mine.MyStrayFragment

class ItemCollectionFragment : Fragment() {

    private lateinit var binding : FragmentItemCollectionBinding
    private val viewModel : ItemCollectionViewModel by viewModels()

    companion object {
        var viewPagerCurrentPosition = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemCollectionBinding.inflate(inflater, container, false)

        val navItemList = listOf("日常分享", "走失动物", "流浪动物")

        val navAdapter = CollectionNavAdapter(navItemList, binding.viewPager)
        binding.navBar.adapter = navAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.navBar.layoutManager = layoutManager

        val viewpagerAdapter = CollectionFragmentStateAdapter(this, viewModel.viewPagerList)
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