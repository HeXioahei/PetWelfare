package com.example.petwelfare.ui.adapter.listadapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemStrayBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Stray

class StrayAdapter (private val list: MutableList<Stray>, private val activity: Activity) : RecyclerView.Adapter<StrayAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemStrayBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val headImageInStray = binding.headImageInStray
        val findAddress = binding.findAddress
        val finderMessage = binding.finderMessage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemStrayBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        //...进行数据的处理与呈现
        // 设置头像
//        val lazyHeaders = LazyHeaders.Builder()
//            .addHeader("Authorization", Repository.Authorization)
//            .build()
//        val headImageString = item.photos[0]
//        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
//        holder.headImageInStray.let { Glide.with(activity).load(headImageGlideUrl).into(it) }
        // 设置其他
        holder.findAddress.text = item.address
        holder.finderMessage.text = item.description
        // 还有收藏图标的设置
        // 。。。。。。
    }
}