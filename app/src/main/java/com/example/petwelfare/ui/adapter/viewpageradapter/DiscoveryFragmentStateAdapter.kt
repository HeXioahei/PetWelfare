package com.example.petwelfare.ui.adapter.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.petwelfare.ui.main.discovery.DiscoveryFragment

class DiscoveryFragmentStateAdapter(val fragment: DiscoveryFragment, val list: List<Fragment>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}