package com.example.petwelfare.ui.main.mine

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityMineBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.main.mine.edit.EditMyInfoActivity
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
            binding.itemMineCursor,
            binding.itemPetCursor,
            binding.itemCollectionCursor,
            binding.itemLikeCursor
        )
        viewModel.textList = listOf(
            binding.itemMine,
            binding.itemPet,
            binding.itemCollection,
            binding.itemLike
        )


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

            update()

            // 结束缓冲
            binding.swipeRefresh.isRefreshing = false
        }

        // 跳转到编辑页
        binding.edit.setOnClickListener {
            val intent = Intent(this, EditMyInfoActivity::class.java)
            startActivity(intent)
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
                setCursor(0)
                transaction.replace(R.id.fragment_container_me, viewModel.itemMineFragment)
            }

            "pet" -> {
                setCursor(1)
                transaction.replace(R.id.fragment_container_me, viewModel.itemPetFragment)
            }

            "collection" -> {
                setCursor(2)
                transaction.replace(R.id.fragment_container_me, viewModel.itemCollectionFragment)
            }

            "like" -> {
                setCursor(3)
                transaction.replace(R.id.fragment_container_me, viewModel.itemLikesFragment)
            }
        }
        // 提交事务
        transaction.commit()
    }

    private fun setCursor(index: Int) {
        for (i in 0 until  viewModel.cursorList.size) {
            if (index == i) {
                viewModel.textList[i].typeface = ResourcesCompat.getFont(this, R.font.mf)
                viewModel.textList[i].textSize = (20).toFloat()
                viewModel.cursorList[i].visibility = View.VISIBLE
            } else {
                viewModel.textList[i].typeface = ResourcesCompat.getFont(this, R.font.source_regular)
                viewModel.textList[i].textSize = (15).toFloat()
                viewModel.cursorList[i].visibility = View.INVISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    @SuppressLint("SetTextI18n")
    private fun update() {
        binding.username.text = MineViewModel.userDetail.username
        binding.address.text = MineViewModel.userDetail.address
        binding.personality.text = MineViewModel.userDetail.personality
        binding.fansNum.text = MineViewModel.userDetail.fan_nums.toString()
        binding.followsNum.text = "${MineViewModel.userDetail.follow_nums + MineViewModel.userDetail.collect_orgs_nums}"
        binding.integralsNum.text = MineViewModel.userDetail.integral.toString()
        binding.telephone.text = MineViewModel.userDetail.telephone
        binding.headImage.let {
            Glide.with(this)
                .load(GlideUrl(MineViewModel.userDetail.head_image, Repository.lazyHeaders))
                .into(it)
        }
    }
}