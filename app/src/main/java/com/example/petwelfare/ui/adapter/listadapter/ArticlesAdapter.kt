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
import com.example.petwelfare.databinding.ItemArticleBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.item.itemdetail.ArticleDetailActivity
import java.util.ArrayList

class ArticlesAdapter(private val list: MutableList<Article>) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val followIron = binding.followBtn
        val headImage = binding.headImage
        val username = binding.username
        val time = binding.time
        val articleText = binding.articleText
        val photosContainer = listOf(binding.picture1, binding.picture2, binding.picture3)
        val collectIron = binding.collectIron
        val collectCount = binding.collectCount
        val likeCount = binding.likeCount
        val likeIron = binding.likeIron
        val commentsCount = binding.commentsNums
        val article = binding.article
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemArticleBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        //...进行数据的处理与呈现
        // 呈现头像
        // 设置头像和图片
        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        val myHeadImageString = item.user.head_image
        val headImageGlideUrl = GlideUrl(myHeadImageString, lazyHeaders)
        holder.headImage.let { Glide.with(PetWelfareApplication.context).load(headImageGlideUrl).into(it) }
//        val pictureString1 = item.media[0]
//        val pictureString2 = item.media[1]
//        val pictureString3 = item.media[2]
//        val pictureGlideUrl1 = GlideUrl(pictureString1, lazyHeaders)
//        val pictureGlideUrl2 = GlideUrl(pictureString2, lazyHeaders)
//        val pictureGlideUrl3 = GlideUrl(pictureString3, lazyHeaders)
//        holder.picture1.let { Glide.with(context).load(pictureGlideUrl1).into(it) }
//        holder.picture2.let { Glide.with(context).load(pictureGlideUrl2).into(it) }
//        holder.picture3.let { Glide.with(context).load(pictureGlideUrl3).into(it) }
        for (i in 0 until item.media.size) {
            val photoGlideUrl = GlideUrl(item.media[i])
            holder.photosContainer[i].let { Glide.with(PetWelfareApplication.context).load(photoGlideUrl).into(it) }
        }
        // 设置其他
        if (item.user.follow_status == 0) {
            holder.followIron.setBackgroundResource(R.drawable.img_unfollowed_2)
        } else {
            holder.followIron.setBackgroundResource(R.drawable.img_followed)
        }
        holder.username.text = item.user.username
        holder.time.text = item.time
        holder.articleText.text = item.text
        // 设置点赞和收藏的图标
        if (item.like_status == 1) {
            holder.likeIron.setBackgroundResource(R.drawable.img_liked)
        } else {
            holder.likeIron.setBackgroundResource(R.drawable.img_unliked_2)
        }
        if (item.collect_status == 1) {
            holder.collectIron.setBackgroundResource(R.drawable.img_collected_3)
        } else {
            holder.collectIron.setBackgroundResource(R.drawable.img_uncollected_3)
        }
        holder.collectCount.text = item.collect_nums.toString()
        holder.likeCount.text = item.like_nums.toString()
        holder.commentsCount.text = item.comment_nums.toString()
        // 点击跳转到具体页
        holder.article.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, ArticleDetailActivity::class.java)

            intent.putExtra("username", item.user.username)
            intent.putExtra("userId", item.user.id)
            intent.putExtra("headImage", item.user.head_image)
            intent.putExtra("followStatus", item.user.follow_status)
            intent.putExtra("text", item.text)
            intent.putExtra("time", item.time)
            intent.putExtra("commentsNums", item.comment_nums)
            intent.putExtra("likeNums", item.like_nums)
            intent.putExtra("likeStatus", item.like_status)
            intent.putExtra("collectNums", item.collect_nums)
            intent.putExtra("collectStatus", item.collect_status)
            intent.putExtra("articleId", item.id)
            intent.putStringArrayListExtra("photos", item.media as ArrayList<String>)

            // 检查context是否是Activity的Context，如果不是，则添加FLAG_ACTIVITY_NEW_TASK标志
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            PetWelfareApplication.context.startActivity(intent)

        }
    }
}