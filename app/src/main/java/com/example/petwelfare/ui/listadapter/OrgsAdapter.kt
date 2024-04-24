package com.example.petwelfare.ui.listadapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemOrgBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Org

class OrgsAdapter (private val list: MutableList<Org>, private val activity: Activity) : RecyclerView.Adapter<OrgsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemOrgBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val headImage = binding.headImage
        val contact = binding.OrgContact
        val name = binding.orgName
        val description = binding.OrgDescription
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemOrgBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        //...进行数据的处理与呈现
        // 设置头像
        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        val headImageString = item.headImage
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        holder.headImage.let { Glide.with(activity).load(headImageGlideUrl).into(it) }
        // 设置其他
        holder.contact.text = item.contact
        holder.description.text = item.description
        holder.name.text = item.orgName
    }
}