package com.example.petwelfare.ui.adapter.listadapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemLossBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.ui.item.itemdetail.ArticleDetailActivity
import com.example.petwelfare.ui.item.itemdetail.LossDetailActivity
import okio.blackholeSink

class LossAdapter(private val list: MutableList<Loss>, private val context: Context) : RecyclerView.Adapter<LossAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemLossBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val nameLoss = binding.name
        val addressLoss = binding.address
        val loss = binding.loss
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemLossBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
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
//        holder.headImageInLoss.let { Glide.with(activity).load(headImageGlideUrl).into(it) }
        // 设置其他
        holder.addressLoss.text = item.address
        holder.nameLoss.text = item.name
        // 还有收藏图标的设置
        // 。。。。。。

        // 点击跳转到具体页
        holder.loss.setOnClickListener {
            val intent = Intent(context, LossDetailActivity::class.java)
            context.startActivity(intent)
        }
    }
}