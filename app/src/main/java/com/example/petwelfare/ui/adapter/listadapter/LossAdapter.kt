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
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemLossBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Loss
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.item.itemdetail.LossDetailActivity
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.ArrayList

class LossAdapter(private val list: MutableList<Loss>, val type : String) : RecyclerView.Adapter<LossAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemLossBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val loss = binding.loss
        val petName = binding.name
        val sex = binding.sex
        val address = binding.address
        val lossTime = binding.lossTime
        val contact = binding.contact
        val type = binding.type
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
        val binding  = ItemLossBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
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
                        CoroutineScope(Dispatchers.Main).launch {
                            Log.d("id", item.id.toString())
                            ItemMineViewModel._delMyArticle.value =
                                PetWelfareNetwork.delMyLoss(item.id.toString(), Repository.Authorization)
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
        holder.username.text = item.user.username
        val userHeadImageGlideUrl = GlideUrl(item.user.head_image, Repository.lazyHeaders)
        holder.userHeadImage.let { Glide.with(PetWelfareApplication.context).load(userHeadImageGlideUrl).into(it) }
        // 设置其他
        if (item.photos.isNotEmpty()) {
            val photoGlideUrl = GlideUrl(item.photos[0], Repository.lazyHeaders)
            holder.photoContainer.let { Glide.with(PetWelfareApplication.context).load(photoGlideUrl).into(it) }
        }
        holder.address.text = item.address
        holder.petName.text = item.name
        holder.type.text = item.type
        holder.lossTime.text = item.lost_time
        holder.contact.text = item.contact
        holder.sendTime.text = item.send_time

        if (item.sex == 0) {
            holder.sex.setBackgroundResource(R.drawable.img_sex_female)
        } else {
            holder.sex.setBackgroundResource(R.drawable.img_sex_male)
        }

        if (item.collect_status == 0) {
            holder.collectBtn.setBackgroundResource(R.drawable.img_uncollected_3)
        } else {
            holder.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
        }
        holder.collectCount.text = item.collect_nums.toString()
        holder.commentsCount.text = item.comment_nums.toString()

        // 点击跳转到具体页
        holder.loss.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, LossDetailActivity::class.java)
            intent.putStringArrayListExtra("photos", item.photos as ArrayList<String>)
            intent.putExtra("username", item.user.username)
            intent.putExtra("userId", item.user.id)
            intent.putExtra("headImage", item.user.head_image)
            intent.putExtra("time", item.send_time)
            intent.putExtra("followStatus", item.user.follow_status)

            intent.putExtra("description", item.description)
            intent.putExtra("sex", item.sex)
            intent.putExtra("type", item.type)
            intent.putExtra("address", item.address)
            intent.putExtra("name", item.name)
            intent.putExtra("lostTime", item.lost_time)
            intent.putExtra("description", item.description)
            intent.putExtra("contact", item.contact)

            intent.putExtra("commentNums", item.comment_nums)
            intent.putExtra("collectNums", item.collect_nums)
            intent.putExtra("collectStatus", item.collect_status)
            intent.putExtra("lossId", item.id)
            intent.putStringArrayListExtra("photos", item.photos as ArrayList<String>)

            // 检查context是否是Activity的Context，如果不是，则添加FLAG_ACTIVITY_NEW_TASK标志
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            PetWelfareApplication.context.startActivity(intent)
        }
    }
}