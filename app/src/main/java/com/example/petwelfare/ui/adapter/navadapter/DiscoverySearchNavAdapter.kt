package com.example.petwelfare.ui.adapter.navadapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemNavCommonBinding
import com.example.petwelfare.ui.main.discovery.DiscoveryFragment
import com.example.petwelfare.ui.main.discovery.search.DiscoverySearchActivity

class DiscoverySearchNavAdapter(private val list: List<String>, private val viewPager: ViewPager2) : RecyclerView.Adapter<DiscoverySearchNavAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemNavCommonBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.navItem
        val itemText = binding.navItemText
        val itemCursor = binding.navItemCursor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNavCommonBinding.inflate(
            LayoutInflater.from(PetWelfareApplication.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("NotifyDataSetChanged", "ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.itemText.text = item
        holder.item.setOnClickListener {
            // 导航栏 item 被点击时，光标跟着变化，ViewPager2 的 item 也跟着变化
            viewPager.currentItem = holder.bindingAdapterPosition
            DiscoverySearchActivity.viewPagerCurrentPosition = position
            notifyDataSetChanged()
        }
        // 设置光标和背景的变化
        val currentPosition = DiscoverySearchActivity.viewPagerCurrentPosition
        if (currentPosition == position) {
            holder.itemText.setTextColor(Color.BLACK)
            holder.itemText.paint.isFakeBoldText = true
            holder.itemCursor.visibility = View.VISIBLE
        } else {
            holder.itemText.setTextColor(Color.GRAY)
            holder.itemText.paint.isFakeBoldText = false
            holder.itemCursor.visibility = View.INVISIBLE
        }
    }

}