package com.example.petwelfare.ui.viewpageradapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.replace
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemNavHeadBinding
import com.example.petwelfare.databinding.ItemViewpagerBinding

class HeadViewPagerAdapter(val list: List<Fragment>, val childFragmentManager: FragmentManager): RecyclerView.Adapter<HeadViewPagerAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemViewpagerBinding) : ViewHolder(binding.root) {
        val viewPagerItem = binding.itemViewPager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemViewpagerBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        // 创建FragmentTransaction实例
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()

        transaction.replace(R.id.item_viewPager, item)   // 此处有问题

        transaction.commit()
    }

}