package com.example.petwelfare.ui.main.mine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityMineBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.main.mine.edit.EditMyInfoActivity
import com.example.petwelfare.ui.main.mine.item.like.ItemLikesFragment
import com.example.petwelfare.ui.main.mine.item.pet.ItemPetFragment
import com.example.petwelfare.ui.main.mine.item.collection.ItemCollectionFragment
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineFragment
import com.example.petwelfare.ui.main.mine.users.FansActivity
import com.example.petwelfare.ui.main.mine.users.FollowsActivity

class MineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMineBinding

    private val viewModel: MineViewModel by viewModels()

    private val fragmentManager = supportFragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)
        ActivityCollector.mineActivity = this

        viewModel.cursorList = listOf(
            binding.itemMineCursor as AppCompatImageView,
            binding.itemPetCursor as AppCompatImageView,
            binding.itemCollectionCursor as AppCompatImageView,
            binding.itemLikeCursor as AppCompatImageView
        )

        // 设置其他信息
//        binding.username.text = "aaaaaaaaa"
//        binding.address.text = "aaaaaaaaa"
//        binding.personality.text = "aaaaaaaaa"
//        binding.fansNum.text = "aaaaaaaaa"
//        binding.followsNum.text = "aaaaaaaaa"
//        binding.integralsNum.text = "aaaaaaaaa"
//        binding.telephone.text = "aaaaaaaaa"

        // 创建viewModel
//        val viewModel: MineViewModel by viewModels()
        // 参考官方文档：
        // https://developer.android.google.cn/topic/libraries/architecture/viewmodel?hl=zh-cn
        // 需引入依赖：implementation("androidx.activity:activity-ktx:1.7.0")
        // Create a ViewModel the first time the system calls an activity's onCreate() method.
        // Re-created activities receive the same DiceRollViewModel instance created by the first activity.
        // Use the 'by viewModels()' Kotlin property delegate
        // from the activity-ktx artifact

//        // 应用 viewModel
//        // 参看书中第623页
//        viewModel.setUserId(Repository.myId)
//
//        // 当 myDetailData 中有任何数据变化时，就会回调传入的这个Observe接口中
//        viewModel.myDetailData.observe(this) { result ->
//            Log.d("getUserInfo", "going2")
//            val user = result.getOrNull()
//            if (user != null) {
//                viewModel.myDetail = user
//                // 进行页面呈现的变化
//
//                // 设置头像
//                val myHeadImageString = viewModel.myDetail.headImage
//                val glideUrl = GlideUrl(
//                    myHeadImageString,
//                    LazyHeaders.Builder()
//                        .addHeader("Authorization", Repository.Authorization)
//                        .build()
//                )
//                binding.headImage.let { Glide.with(this).load(glideUrl).into(it) }
//                // 设置其他信息
//                binding.username.text = viewModel.myDetail.username
//                binding.address.text = viewModel.myDetail.address
//                binding.personality.text = viewModel.myDetail.personality
//                binding.fansNum.text = viewModel.myDetail.fanNums.toString()
//                binding.followsNum.text = viewModel.myDetail.followNums.toString()
//                binding.integralsNum.text = viewModel.myDetail.integral.toString()
//                binding.telephone.text = viewModel.myDetail.telephone
//
//                binding.swipeRefresh.isRefreshing = false
//                //Toast.makeText(this, "刷新完毕", Toast.LENGTH_SHORT).show()
//
//            } else {
//                Toast.makeText(this, "未能获取用户信息", Toast.LENGTH_SHORT).show()
//                result.exceptionOrNull()?.printStackTrace()
//            }
//        }
//
//        // 下拉刷新，更新用户信息
//        binding.swipeRefresh.setOnRefreshListener {
//            Log.d("swipeRefresh", "doing")
//            viewModel.myDetailData = Repository                      // 不确定此处是否有问题
//                .getUserInfo(Repository.myId, Repository.Authorization)    // 可以确定了，此处有问题，不能这样来调用getUserInfo()，只能通过InfoLiveData的改变
//        }

        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 一开始获取信息
        binding.swipeRefresh.isRefreshing = true
        viewModel.getUserDetail(Repository.myId)
        viewModel.delayAction {
            binding.swipeRefresh.isRefreshing = false  // 过久未响应，自动结束缓冲
        }

        // 缓冲并获取信息 / 手动刷新
        binding.swipeRefresh.setOnRefreshListener {
            // 获取信息
            viewModel.getUserDetail(Repository.myId)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false  // 过久未响应，自动结束缓冲
            }
        }

        // 显示信息
        MineViewModel.userDetailLiveData.observe(this) { result->

            MineViewModel.userDetail = result

            Log.d("userDetail", result.toString())
            binding.username.text = MineViewModel.userDetail.username
            binding.address.text = MineViewModel.userDetail.address
            binding.personality.text = MineViewModel.userDetail.personality
            binding.fansNum.text = MineViewModel.userDetail.fan_nums.toString()
            binding.followsNum.text = MineViewModel.userDetail.follow_nums.toString()
            binding.integralsNum.text = MineViewModel.userDetail.integral.toString()
            binding.telephone.text = MineViewModel.userDetail.telephone

            // 设置头像
            val glideUrl = GlideUrl(
                MineViewModel.userDetail.head_image,
                LazyHeaders.Builder()
                    .addHeader("Authorization", Repository.Authorization)
                    .build()
            )
            binding.headImage.let { Glide.with(this).load(glideUrl).into(it) }

            // 结束缓冲
            binding.swipeRefresh.isRefreshing = false
        }

        // 跳转到编辑页
        binding.edit.setOnClickListener {
            val intent = Intent(this, EditMyInfoActivity::class.java)
            startActivity(intent)
//            intent.putExtra("headImage", viewModel.userDetail.head_image)
//            intent.putExtra("username", viewModel.userDetail.username)
//            intent.putExtra("address", viewModel.userDetail.address)
//            intent.putExtra("personality", viewModel.userDetail.personality)
//            intent.putExtra("telephone", viewModel.userDetail.telephone)
        }

        binding.fans.setOnClickListener {
            val intent = Intent(this, FansActivity::class.java)
            startActivity(intent)
        }

        binding.follows.setOnClickListener {
            val intent = Intent(this, FollowsActivity::class.java)
            startActivity(intent)
        }

        // 默认先显示个人页
        replaceFragment("mine")

        binding.itemMine.setOnClickListener {
            replaceFragment("mine")
        }
        binding.itemPet.setOnClickListener {
            replaceFragment("pet")
        }
        binding.itemCollection.setOnClickListener {
            replaceFragment("collection")
        }
        binding.itemLike.setOnClickListener {
            replaceFragment("like")
        }

    }

    // 更换fragment
    private fun replaceFragment(type: String) {
        // 创建FragmentTransaction实例
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        // 添加fragment
        when (type) {
            "mine" -> {
                setCursorVisibility(0)
                transaction.replace(R.id.fragment_container_me, viewModel.itemMineFragment)
            }

            "pet" -> {
                setCursorVisibility(1)
                transaction.replace(R.id.fragment_container_me, viewModel.itemPetFragment)
            }

            "collection" -> {
                setCursorVisibility(2)
                transaction.replace(R.id.fragment_container_me, viewModel.itemCollectionFragment)
            }

            "like" -> {
                setCursorVisibility(3)
                transaction.replace(R.id.fragment_container_me, viewModel.itemLikesFragment)
            }
        }
        // 提交事务
        transaction.commit()
    }

    private fun setCursorVisibility(index: Int) {
        for (i in 0 until  viewModel.cursorList.size) {
            if (index == i) {
                viewModel.cursorList[i].visibility = View.VISIBLE
            } else {
                viewModel.cursorList[i].visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}