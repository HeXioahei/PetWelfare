package com.example.petwelfare.ui.adapter.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class CommonFragmentStateAdapter(val activity: FragmentActivity, val list: List<Fragment>): FragmentStateAdapter(activity) {

    override fun getItemCount(): Int  = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}