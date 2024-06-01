package com.example.petwelfare.ui.adapter.listadapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemUserBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.UserBrief
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.main.otheruser.OtherUserDetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersAdapter (private val list: MutableList<UserBrief>, private val lifecycleOwner: LifecycleOwner, private val context: Context) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val name = binding.username
        val headImage = binding.userHeadImage
        val personality = binding.personality
        val btn = binding.isFollowedBtn
        val user = binding.user
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
        val headImageString = item.head_image
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        holder.headImage.let { Glide.with(context).load(headImageGlideUrl).into(it) }
        holder.personality.text = item.personality
        holder.name.text = item.name

        Log.d("follow_status", item.follow_status.toString())

        // 后端在返回列表的时候并没有返回是否关注的状态码，所以不显示是否关注的状态

//        if (item.follow_status == 0) {
//            holder.btn.setBackgroundResource(R.drawable.img_unfollowed_2)
//        } else {
//            holder.btn.setBackgroundResource(R.drawable.img_followed)
//        }


//        holder.btn.setOnClickListener {
//            val _followResponse = MutableLiveData<BaseResponse>()
//            val followResponse : LiveData<BaseResponse> = _followResponse
//            CoroutineScope(Dispatchers.Main).launch {
//                _followResponse.value = PetWelfareNetwork.follow(item.id.toString(), Repository.Authorization)
//            }
//            followResponse.observe(lifecycleOwner) {
//                item.follow_status = item.follow_status xor 1
//                if (item.follow_status == 0) {
//                    holder.btn.setBackgroundResource(R.drawable.img_unfollowed_2)
//                } else {
//                    holder.btn.setBackgroundResource(R.drawable.img_followed)
//                }
//            }
//        }

        holder.user.setOnClickListener {
            val intent = Intent(context, OtherUserDetailActivity::class.java)
            intent.putExtra("userId", item.id)
            context.startActivity(intent)
        }
    }
}