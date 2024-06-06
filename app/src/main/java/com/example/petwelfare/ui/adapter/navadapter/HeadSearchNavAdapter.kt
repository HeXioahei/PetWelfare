package com.example.petwelfare.ui.adapter.navadapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemNavHeadBinding
import com.example.petwelfare.ui.main.discovery.search.SearchStrayFragment
import com.example.petwelfare.ui.main.head.HeadFragment
import com.example.petwelfare.ui.main.head.search.HeadSearchActivity
import org.w3c.dom.Text

class HeadSearchNavAdapter(private val list: List<String>, private val viewPager: ViewPager2) : RecyclerView.Adapter<HeadSearchNavAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemNavHeadBinding) : ViewHolder(binding.root) {
        val item = binding.navItem
        val itemText = binding.navItemText
        val itemCursor = binding.navItemCursor
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNavHeadBinding.inflate(
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
        holder.itemText.text = item
        holder.item.setOnClickListener {
            // 导航栏 item 被点击时，光标跟着变化，ViewPager2 的 item 也跟着变化
            viewPager.currentItem = holder.bindingAdapterPosition
            HeadSearchActivity.viewPagerCurrentPosition = position
            notifyDataSetChanged()
        }
        // 设置光标和背景的变化
        val currentPosition = HeadSearchActivity.viewPagerCurrentPosition

        if (currentPosition == position) {
            holder.itemCursor.visibility = View.VISIBLE
            holder.itemText.paint.isFakeBoldText = true
            holder.itemText.typeface = ResourcesCompat.getFont(PetWelfareApplication.context, R.font.mf)
            holder.itemText.textSize = (20).toFloat()
        } else {
            holder.itemCursor.visibility = View.INVISIBLE
            holder.itemText.paint.isFakeBoldText = false
            holder.itemText.typeface = ResourcesCompat.getFont(PetWelfareApplication.context, R.font.source_regular)
            holder.itemText.textSize = (15).toFloat()
        }
    }

}