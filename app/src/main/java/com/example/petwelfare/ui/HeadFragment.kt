package com.example.petwelfare.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentHeadBinding
import com.example.petwelfare.ui.head.ItemFollowFragment
import com.example.petwelfare.ui.head.ItemNearFragment
import com.example.petwelfare.ui.head.ItemRecommendFragment
import com.example.petwelfare.ui.head.ItemSquareFragment

class HeadFragment : Fragment() {

    private lateinit var binding : FragmentHeadBinding

    // 获取FragmentManager
    private val fragmentManager: FragmentManager = ActivityCollector.mainActivity.supportFragmentManager
    // 创建Fragment实例
    private val itemSquareFragment = ItemSquareFragment(ActivityCollector.mainActivity)
    private val itemRecommendFragment = ItemRecommendFragment(ActivityCollector.mainActivity)
    private val itemNearFragment = ItemNearFragment(ActivityCollector.mainActivity)
    private val itemFollowFragment = ItemFollowFragment(ActivityCollector.mainActivity)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeadBinding.inflate(inflater, container, false)

        replaceFragment("square")

        binding.toSquareBtn.setOnClickListener {
            replaceFragment("square")
        }
        binding.toRecommendBtn.setOnClickListener {
            replaceFragment("recommend")
        }
        binding.toFollowBtn.setOnClickListener {
            replaceFragment("follow")
        }
        binding.toNearBtn.setOnClickListener {
            replaceFragment("near")
        }

        return binding.root
    }

    // 更换fragment
    private fun replaceFragment(type: String) {
        // 创建FragmentTransaction实例
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        // 添加fragment
        when (type) {
            "square" -> {
                transaction.replace(R.id.fragmentHead, itemSquareFragment)
            }
            "recommend" -> {
                transaction.replace(R.id.fragmentHead, itemRecommendFragment)
            }
            "follow" -> {
                transaction.replace(R.id.fragmentHead, itemFollowFragment)
            }
            "near" -> {
                transaction.replace(R.id.fragmentHead, itemNearFragment)
            }
        }
        // 提交事务
        transaction.commit()
    }
}