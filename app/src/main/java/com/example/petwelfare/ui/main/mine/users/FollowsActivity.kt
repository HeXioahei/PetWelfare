package com.example.petwelfare.ui.main.mine.users

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityFollowsBinding
import com.example.petwelfare.ui.adapter.listadapter.OrgsAdapter
import com.example.petwelfare.ui.adapter.listadapter.UsersAdapter
import com.example.petwelfare.ui.adapter.navadapter.DiscoveryNavAdapter
import com.example.petwelfare.ui.adapter.navadapter.FollowsNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.DiscoveryFragmentStateAdapter
import com.example.petwelfare.ui.main.discovery.DiscoveryFragment

class FollowsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFollowsBinding
//    private val navItemList = listOf("关注的用户", "关注的组织")
//
//    companion object {
//        val viewPagerCurrentPosition = 0
//    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCollector.addActivity(this)

        binding = ActivityFollowsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel : FollowsViewModel by viewModels()

//        val navAdapter = FollowsNavAdapter(navItemList, binding.viewPager)
//        binding.navBar.adapter = navAdapter
//        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
//        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
//        binding.navBar.layoutManager = layoutManager
//
//        val viewpagerAdapter = DiscoveryFragmentStateAdapter(this, viewModel.viewPagerList)
//        binding.viewPager.adapter = viewpagerAdapter
//
//        // ViewPager2 的 item 变化，导航栏跟着变化，导航栏的光标也跟着变化
//        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            @SuppressLint("NotifyDataSetChanged")
//            override fun onPageSelected(position: Int) {
//                DiscoveryFragment.viewPagerCurrentPosition = position
//                binding.navBar.scrollToPosition(position)
//                navAdapter.notifyDataSetChanged()
//            }
//        })
//

        viewModel.getFollows()
        viewModel.getCollectOrgs()
        binding.swipeRefresh.isRefreshing = true
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getFollows()
            viewModel.getCollectOrgs()
        }

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        val followsAdapter = UsersAdapter(viewModel.followsList)
        val orgsAdapter = OrgsAdapter(viewModel.orsList)

        binding.recyclerView.adapter = followsAdapter
        binding.recyclerView.layoutManager = layoutManager
        binding.follows.typeface = ResourcesCompat.getFont(this, R.font.mf)
        binding.orgs.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
        binding.followsCursor.visibility = View.VISIBLE
        binding.orgsCursor.visibility = View.INVISIBLE

        binding.follows.setOnClickListener {
            binding.recyclerView.adapter = followsAdapter
            if (viewModel.followsList.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            binding.follows.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.orgs.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
            binding.followsCursor.visibility = View.VISIBLE
            binding.orgsCursor.visibility = View.INVISIBLE
        }

        binding.orgs.setOnClickListener {
            binding.recyclerView.adapter = orgsAdapter
            if (viewModel.orsList.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            binding.orgs.typeface = ResourcesCompat.getFont(this, R.font.mf)
            binding.follows.typeface = ResourcesCompat.getFont(this, R.font.source_medium)
            binding.followsCursor.visibility = View.INVISIBLE
            binding.orgsCursor.visibility = View.VISIBLE
        }

        viewModel.followsListLiveData.observe(this) { result->
            if (binding.recyclerView.adapter == followsAdapter) {
                if (result.data.follows.isNotEmpty()) binding.image.visibility = View.INVISIBLE
                else binding.image.visibility = View.VISIBLE
            }
            viewModel.followsList.clear()
            viewModel.followsList.addAll(result.data.follows)
            followsAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        viewModel.collectOrgsList.observe(this) { result->
            if (binding.recyclerView.adapter == orgsAdapter) {
                if (result.data.org_list.isNotEmpty()) binding.image.visibility = View.INVISIBLE
                else binding.image.visibility = View.VISIBLE
            }
            viewModel.orsList.clear()
            viewModel.orsList.addAll(result.data.org_list)
            followsAdapter.notifyDataSetChanged()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}