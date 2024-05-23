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
import com.example.petwelfare.databinding.FragmentItemCollectionBinding
import com.example.petwelfare.ui.BlankFragment
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.LossAdapter
import com.example.petwelfare.ui.adapter.listadapter.OrgsAdapter
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
    //private lateinit var navList: MutableList<View>

    private val mineActivity = ActivityCollector.mineActivity

    private val navItemList = listOf("日常分享", "走失动物", "流浪动物")
    private val viewPagerList: List<Fragment> = listOf(
        CollectArticlesFragment(),
        CollectLossFragment(),
        CollectStrayFragment()
    )

    companion object {
        var viewPagerCurrentPosition = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemCollectionBinding.inflate(inflater, container, false)

//        val activity = activity as com.example.petwelfare.ui.main.mine.MineActivity
//
//        val viewModel: ItemCollectionViewModel by viewModels()
//
//        navList = mutableListOf(binding.article, binding.loss, binding.stray, binding.orgs)
//
//        viewModel.collectArticlesData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.collectArticles = list
//            }
//        }
//
//        viewModel.collectLossData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.collectLoss = list
//            }
//        }
//
//        viewModel.collectStrayData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.collectStray = list
//            }
//        }
//
//        viewModel.collectOrgData.observe(activity) { result->
//            val list = result.getOrNull()
//            if (list != null) {
//                viewModel.collectOrg = list
//            }
//        }
//
//        val layoutInflater = LinearLayoutManager(activity)
//        layoutInflater.orientation = LinearLayoutManager.VERTICAL
//        val collectArticleAdapter0 = ArticlesAdapter(viewModel.collectArticles, activity)
//        binding.collectionRecyclerView.adapter = collectArticleAdapter0
//        binding.collectionRecyclerView.layoutManager = layoutInflater
//
//
//
//        binding.article.setOnClickListener {
//            cursorMove(binding.article)
//            val collectArticleAdapter = ArticlesAdapter(viewModel.collectArticles, activity)
//            binding.collectionRecyclerView.adapter = collectArticleAdapter
//            binding.collectionRecyclerView.layoutManager = layoutInflater
//        }
//
//        binding.loss.setOnClickListener {
//            cursorMove(binding.loss)
//            val collectLossAdapter = LossAdapter(viewModel.collectLoss, activity)
//            binding.collectionRecyclerView.adapter = collectLossAdapter
//            binding.collectionRecyclerView.layoutManager = layoutInflater
//        }
//
//        binding.stray.setOnClickListener {
//            cursorMove(binding.stray)
//            val collectStrayAdapter = StrayAdapter(viewModel.collectStray, activity)
//            binding.collectionRecyclerView.adapter = collectStrayAdapter
//            binding.collectionRecyclerView.layoutManager = layoutInflater
//        }
//
//        binding.orgs.setOnClickListener {
//            cursorMove(binding.orgs)
//            val collectOrgAdapter = OrgsAdapter(viewModel.collectOrg, activity)
//            binding.collectionRecyclerView.adapter = collectOrgAdapter
//            binding.collectionRecyclerView.layoutManager = layoutInflater
//        }

        val navAdapter = CollectionNavAdapter(navItemList, binding.viewPager)
        binding.navBar.adapter = navAdapter
        val layoutManager = LinearLayoutManager(mineActivity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.navBar.layoutManager = layoutManager

        val viewpagerAdapter = CollectionFragmentStateAdapter(this, viewPagerList)
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