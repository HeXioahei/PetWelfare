package com.example.petwelfare.ui.adapter.viewpageradapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemViewpagerMineBinding
import com.example.petwelfare.ui.main.head.HeadFragment
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineFragment

class MineFragmentStateAdapter(val fragment: ItemMineFragment, val list: List<Fragment>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int  = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}