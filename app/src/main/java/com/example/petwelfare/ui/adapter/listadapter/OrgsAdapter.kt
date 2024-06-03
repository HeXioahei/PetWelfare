package com.example.petwelfare.ui.adapter.listadapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemOrgBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Org
import com.example.petwelfare.ui.item.itemdetail.OrgDetailActivity

class OrgsAdapter (private val list: MutableList<Org>) : RecyclerView.Adapter<OrgsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemOrgBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val headImage = binding.headImage
        val followImg = binding.isFollowedBtn
        val fans = binding.collectNums
        val contact = binding.OrgContact
        val name = binding.orgName
        val description = binding.OrgDescription
        val org = binding.org
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
        val headImageString = item.head_image
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        holder.headImage.let { Glide.with(PetWelfareApplication.context).load(headImageGlideUrl).into(it) }
        // 设置其他
        holder.contact.text = item.contract
        holder.description.text = item.description
        holder.name.text = item.org_name
        holder.fans.text = item.collect_nums.toString()

        if (item.collect_status == 0) {
            holder.followImg.setBackgroundResource(R.drawable.img_unfollowed_2)
        } else {
            holder.followImg.setBackgroundResource(R.drawable.img_followed)
        }

        holder.org.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, OrgDetailActivity::class.java)
            intent.putExtra("org_name", item.org_name)
            intent.putExtra("id", item.id)
            intent.putExtra("head_image", item.head_image)
            intent.putExtra("collect_nums", item.collect_nums)
            intent.putExtra("collect_status", item.collect_status)
            intent.putExtra("description", item.description)
            intent.putExtra("contact", item.contract)
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            PetWelfareApplication.context.startActivity(intent)
        }
    }
}