package com.example.petwelfare.ui.listadapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemPetBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Pet

class PetsAdapter (private val list: MutableList<Pet>, private val activity: Activity) : RecyclerView.Adapter<PetsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPetBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val headImage = binding.petHeadImage
        val name = binding.petName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemPetBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
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
        val headImageString = item.head_image
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        holder.headImage.let { Glide.with(activity).load(headImageGlideUrl).into(it) }
    }
}