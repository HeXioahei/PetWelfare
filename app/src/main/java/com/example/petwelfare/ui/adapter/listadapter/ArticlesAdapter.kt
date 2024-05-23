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
import com.example.petwelfare.databinding.ItemArticleBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.ui.item.itemdetail.ArticleDetailActivity

class ArticlesAdapter(private val list: MutableList<Article>, private val activity: Activity) : RecyclerView.Adapter<ArticlesAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemArticleBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val headImage = binding.headImage
        val username = binding.username
        val articleText = binding.articleText
        val picture1 = binding.picture1
        val picture2 = binding.picture2
        val picture3 = binding.picture3
        val hitIron = binding.hitIron
        val collectIron = binding.collectIron
        val likeIron = binding.likeIron
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
        val myHeadImageString = item.user.headImage
//        val pictureString1 = item.media[0]
//        val pictureString2 = item.media[1]
//        val pictureString3 = item.media[2]
//        val headImageGlideUrl = GlideUrl(myHeadImageString, lazyHeaders)
//        val pictureGlideUrl1 = GlideUrl(pictureString1, lazyHeaders)
//        val pictureGlideUrl2 = GlideUrl(pictureString2, lazyHeaders)
//        val pictureGlideUrl3 = GlideUrl(pictureString3, lazyHeaders)
//        holder.headImage.let { Glide.with(activity).load(headImageGlideUrl).into(it) }
//        holder.picture1.let { Glide.with(activity).load(pictureGlideUrl1).into(it) }
//        holder.picture2.let { Glide.with(activity).load(pictureGlideUrl2).into(it) }
//        holder.picture3.let { Glide.with(activity).load(pictureGlideUrl3).into(it) }
        // 设置其他
        holder.username.text = item.user.username
        holder.articleText.text = item.text
        // 设置点赞和收藏的图标
        if (item.likeStatus == 1) {

        } else {

        }
        if (item.collectStatus == 1) {

        } else {

        }
        // 点击跳转到具体页
        holder.article.setOnClickListener {
            val intent = Intent(activity, ArticleDetailActivity::class.java)
            activity.startActivity(intent)
        }
    }
}