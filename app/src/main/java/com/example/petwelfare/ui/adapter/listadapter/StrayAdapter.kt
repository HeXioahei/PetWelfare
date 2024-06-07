package com.example.petwelfare.ui.adapter.listadapter

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemStrayBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Stray
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.item.itemdetail.StrayDetailActivity
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class StrayAdapter (private val list: MutableList<Stray>, val type: String) : RecyclerView.Adapter<StrayAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemStrayBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val stray = binding.stray
        val address = binding.address
        val findTime = binding.findTime
        val username = binding.username
        val userHeadImage = binding.userHeadImage
        val sendTime = binding.sendTime
        val photoContainer = binding.photoContainer
        val collectBtn = binding.collectBtn
        val collectCount = binding.collectCount
        val commentsCount = binding.commentsCount

        val toMenuBtn = binding.toMenu
        val delBtn = binding.delBtn
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemStrayBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        if (type == "me") {
            holder.toMenuBtn.visibility = View.VISIBLE
            holder.delBtn.visibility = View.GONE

            holder.toMenuBtn.setOnClickListener {
                if (holder.delBtn.visibility == View.GONE) {
                    holder.delBtn.visibility = View.VISIBLE
                    holder.delBtn.setOnClickListener {
                        Log.d("id", item.id.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            ItemMineViewModel._delMyArticle.value =
                                PetWelfareNetwork.delMyStray(item.id.toString(), Repository.Authorization)
                        }
                    }
                } else {
                    holder.delBtn.visibility = View.GONE
                }
            }
        } else {
            holder.toMenuBtn.visibility = View.GONE
            holder.delBtn.visibility = View.GONE
        }

        //...进行数据的处理与呈现
        // 设置头像
        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        val headImageString = item.user.head_image
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        holder.userHeadImage.let { Glide.with(PetWelfareApplication.context).load(headImageGlideUrl).into(it) }
        holder.username.text = item.user.username
        // 设置其他
        if (item.photos.size!=0) {
            val photoGlideUrl = GlideUrl(item.photos[0], Repository.lazyHeaders)
            holder.photoContainer.let { Glide.with(PetWelfareApplication.context).load(photoGlideUrl).into(it) }
        }
        holder.address.text = item.address
        holder.findTime.text = item.time
        holder.sendTime.text = item.time
        if (item.collect_status == 0) {
            holder.collectBtn.setBackgroundResource(R.drawable.img_uncollected_3)
        } else {
            holder.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
        }
        holder.collectCount.text = item.collect_nums.toString()
        holder.commentsCount.text = item.comments_nums.toString()

        holder.stray.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, StrayDetailActivity::class.java)
            intent.putStringArrayListExtra("photos", item.photos as ArrayList<String>)
            intent.putExtra("username", item.user.username)
            intent.putExtra("userId", item.user.id)
            intent.putExtra("headImage", item.user.head_image)
            intent.putExtra("time", item.time)
            intent.putExtra("followStatus", item.user.follow_status)

            intent.putExtra("description", item.description)
            intent.putExtra("address", item.address)

            intent.putExtra("commentNums", item.comments_nums)
            intent.putExtra("collectNums", item.collect_nums)
            intent.putExtra("collectStatus", item.collect_status)
            intent.putExtra("strayId", item.id)

            // 检查context是否是Activity的Context，如果不是，则添加FLAG_ACTIVITY_NEW_TASK标志
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            PetWelfareApplication.context.startActivity(intent)
        }
    }
}