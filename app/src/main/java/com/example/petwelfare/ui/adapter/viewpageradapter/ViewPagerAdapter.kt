package com.example.petwelfare.ui.adapter.viewpageradapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemNavCommonBinding
import com.example.petwelfare.databinding.ItemViewpagerBinding
import com.example.petwelfare.ui.adapter.navadapter.CollectionNavAdapter
import com.example.petwelfare.ui.main.mine.item.collection.ItemCollectionFragment

class ViewPagerAdapter(private val list: List<Fragment>, private val activity: AppCompatActivity) : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val viewPager = binding.viewPager
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemViewpagerBinding.inflate(
            LayoutInflater.from(PetWelfareApplication.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        val fragmentManager = activity.supportFragmentManager
        val traction = fragmentManager.beginTransaction()
        traction.add(holder.viewPager.id, item)
        traction.commit()
    }
}