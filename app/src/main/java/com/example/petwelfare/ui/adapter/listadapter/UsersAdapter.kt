package com.example.petwelfare.ui.adapter.listadapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemUserBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.UserBrief

class UsersAdapter (private val list: MutableList<UserBrief>, private val activity: Activity) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val name = binding.username
        val headImage = binding.userHeadImage
        val personality = binding.personality
        val btn = binding.isFollowedBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemUserBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        //...进行数据的处理与呈现
        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        val headImageString = item.headImage
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        holder.headImage.let { Glide.with(activity).load(headImageGlideUrl).into(it) }
        holder.personality.text = item.personality
        holder.name.text = item.username
    }
}