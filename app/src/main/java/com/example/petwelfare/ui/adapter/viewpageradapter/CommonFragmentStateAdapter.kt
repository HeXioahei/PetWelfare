package com.example.petwelfare.ui.adapter.viewpageradapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.ui.main.head.HeadFragment
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineFragment

class CommonFragmentStateAdapter(val activity: FragmentActivity, val list: List<Fragment>): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int  = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}