package com.example.petwelfare.ui.adapter.viewpageradapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemViewpagerBinding
import com.example.petwelfare.logic.Repository

class ViewPagerAdapter(private val list: List<String>) : RecyclerView.Adapter<ViewPagerAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val photo = binding.photo
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
        val photoGlideUrl = GlideUrl(item, Repository.lazyHeaders)
        holder.photo.let { Glide.with(PetWelfareApplication.context).load(photoGlideUrl).into(it) }
    }
}