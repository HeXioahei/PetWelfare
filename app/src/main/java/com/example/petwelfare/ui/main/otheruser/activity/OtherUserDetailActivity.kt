package com.example.petwelfare.ui.main.otheruser.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityOtherUserDetailBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.adapter.navadapter.MineNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.CommonFragmentStateAdapter
import com.example.petwelfare.ui.main.mine.MineViewModel
import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.MyArticlesFragment
import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.MyLossFragment
import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.MyStrayFragment
import com.example.petwelfare.ui.main.mine.itemlist.pet.ItemPetFragment
import com.example.petwelfare.ui.main.otheruser.viewmodel.OtherUserDetailViewModel

class OtherUserDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOtherUserDetailBinding

    private val navItemList = listOf("日常分享", "走失动物", "流浪动物", "宠物屋")

    private val viewModel : OtherUserDetailViewModel by viewModels()

    companion object {
        var viewPagerCurrentPosition = 0
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtherUserDetailBinding.inflate(layoutInflater)

        ActivityCollector.addActivity(this)
        ActivityCollector.otherUserActivity = this

        setContentView(binding.root)

        viewModel.otherUserDetail.id = intent.getLongExtra("userId", -1)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 一开始获取信息
        binding.swipeRefresh.isRefreshing = true
        viewModel.getUserDetail(viewModel.otherUserDetail.id)
        viewModel.delayAction {
            binding.swipeRefresh.isRefreshing = false  // 过久未响应，自动结束缓冲
        }

        // 缓冲并获取信息 / 手动刷新
        binding.swipeRefresh.setOnRefreshListener {
            // 获取信息
            viewModel.getUserDetail(viewModel.otherUserDetail.id)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false  // 过久未响应，自动结束缓冲
            }
        }

        // 显示信息
        viewModel.otherUserDetailLiveData.observe(this) { result->

            viewModel.otherUserDetail = result

            Log.d("userDetail", result.toString())

            update()

            // 结束缓冲
            binding.swipeRefresh.isRefreshing = false
        }

        // 点击进行关注
        binding.followBtn.setOnClickListener {
            viewModel.follow(viewModel.otherUserDetail.id.toString())
        }
        viewModel.followResponse.observe(this) {
            Log.d("follow_status", viewModel.otherUserDetail.follow_status.toString())
            viewModel.otherUserDetail.follow_status = viewModel.otherUserDetail.follow_status xor 1
            if (viewModel.otherUserDetail.follow_status == 0) {
                binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
            } else {
                binding.followBtn.setBackgroundResource(R.drawable.img_followed)
            }
        }

        val viewPagerList: List<Fragment> =
            listOf(
                MyArticlesFragment(viewModel.otherUserDetail.id),
                MyLossFragment(viewModel.otherUserDetail.id),
                MyStrayFragment(viewModel.otherUserDetail.id),
                ItemPetFragment(viewModel.otherUserDetail.id)
            )

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

    @SuppressLint("SetTextI18n")
    private fun update() {
        binding.username.text = viewModel.otherUserDetail.username
        binding.address.text = viewModel.otherUserDetail.address
        binding.personality.text = viewModel.otherUserDetail.personality
        binding.fansNum.text = viewModel.otherUserDetail.fan_nums.toString()
        binding.followsNum.text = "${MineViewModel.userDetail.follow_nums + MineViewModel.userDetail.collect_orgs_nums}"
        binding.integralsNum.text = viewModel.otherUserDetail.integral.toString()
        binding.telephone.text = viewModel.otherUserDetail.telephone
        binding.headImage.let {
            Glide.with(this)
                .load(GlideUrl(viewModel.otherUserDetail.head_image, Repository.lazyHeaders))
                .into(it)
        }
        if (viewModel.otherUserDetail.follow_status == 0) {
            binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
        } else {
            binding.followBtn.setBackgroundResource(R.drawable.img_followed)
        }
    }
}