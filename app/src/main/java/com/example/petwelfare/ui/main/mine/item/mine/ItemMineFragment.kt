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
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentItemMineBinding
import com.example.petwelfare.ui.BlankFragment
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.adapter.listadapter.StrayAdapter
import com.example.petwelfare.ui.adapter.navadapter.DiscoveryNavAdapter
import com.example.petwelfare.ui.adapter.navadapter.MineNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.DiscoveryFragmentStateAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.MineFragmentStateAdapter
import com.example.petwelfare.ui.main.discovery.DiscoveryFragment
import com.example.petwelfare.ui.main.discovery.ItemAdoptionFragment
import com.example.petwelfare.ui.main.discovery.ItemFosterFragment
import com.example.petwelfare.ui.main.discovery.ItemLossFragment
import com.example.petwelfare.ui.main.discovery.ItemRescueFragment
import com.example.petwelfare.ui.main.discovery.ItemStrayFragment
import com.example.petwelfare.ui.main.mine.MineActivity

class ItemMineFragment : Fragment() {



    private lateinit var binding : FragmentItemMineBinding
//    private lateinit var navList: MutableList<View>

//    private val mineActivity = ActivityCollector.mineActivity

//    private val navItemList = listOf("全部", "日常分享", "走失动物", "流浪动物", "收养动物")
//    private val viewPagerList: List<Fragment> = listOf(
//        BlankFragment(),
//        MyArticlesFragment(),
//        MyLossFragment(),
//        MyStrayFragment(),
//        BlankFragment()
//    )

    companion object {
        var viewPagerCurrentPosition = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemMineBinding.inflate(inflater, container, false)

//        navList = mutableListOf(binding.article, binding.loss, binding.stray)
//
//        val activity = activity as MineActivity
//
//        val viewModel: ItemMineViewModel by viewModels()
//        val viewModel2: MineViewModel by viewModels()
//
//        viewModel.setId(viewModel2.myDetail.id)
//
//        viewModel.myArticlesData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.myArticles = list
//            }
//        }
//
//        viewModel.myLossData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.myLoss = list
//            }
//        }
//
//        viewModel.myStrayData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.myStray = list
//            }
//        }
//
//        val layoutInflater = LinearLayoutManager(activity)
//        layoutInflater.orientation = LinearLayoutManager.VERTICAL
//
//        val myArticleAdapter0 = ArticlesAdapter(viewModel.myArticles, activity)
//        binding.itemMineRecyclerView.adapter = myArticleAdapter0
//        binding.itemMineRecyclerView.layoutManager = layoutInflater
//
//        binding.article.setOnClickListener {
//            cursorMove(binding.article)
//            val myArticleAdapter = ArticlesAdapter(viewModel.myArticles, activity)
//            binding.itemMineRecyclerView.adapter = myArticleAdapter
//            binding.itemMineRecyclerView.layoutManager = layoutInflater
//        }
//
//        binding.loss.setOnClickListener {
//            cursorMove(binding.loss)
//            val myLossAdapter = LossAdapter(viewModel.myLoss, activity)
//            binding.itemMineRecyclerView.adapter = myLossAdapter
//            binding.itemMineRecyclerView.layoutManager = layoutInflater
//        }
//
//        binding.stray.setOnClickListener {
//            cursorMove(binding.stray)
//            val myStrayAdapter = StrayAdapter(viewModel.myStray, activity)
//            binding.itemMineRecyclerView.adapter = myStrayAdapter
//            binding.itemMineRecyclerView.layoutManager = layoutInflater
//        }

        val mineActivity = ActivityCollector.mineActivity

        val navItemList = listOf("全部", "日常分享", "走失动物", "流浪动物", "收养动物")

        val viewPagerList: List<Fragment> = listOf(
            BlankFragment(),
            MyArticlesFragment(),
            MyLossFragment(),
            MyStrayFragment(),
            BlankFragment()
        )

        val navAdapter = MineNavAdapter(navItemList, binding.viewPager)
        binding.navBar.adapter = navAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.navBar.layoutManager = layoutManager

        val viewpagerAdapter = MineFragmentStateAdapter(this, viewPagerList)
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