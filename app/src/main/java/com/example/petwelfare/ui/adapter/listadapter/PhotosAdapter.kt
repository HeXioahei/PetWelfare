package com.example.petwelfare.ui.adapter.listadapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemOrgBinding
import com.example.petwelfare.databinding.ItemPetPictureBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.item.itemdetail.OrgDetailActivity
import java.net.URI

class PhotosAdapter(private val listUri: MutableList<Uri>) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPetPictureBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
        val picture = binding.picture
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemPetPictureBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int {
//        if (listUri.isEmpty()) {
//            return listUrl.size
//        }
        return listUri.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        if (listUrl.isEmpty()) {
            val itemUri = listUri[position]
            holder.picture.let {
                Glide.with(PetWelfareApplication.context)
                    .load(itemUri)
                    .into(it)
            }
//        } else {
//            val itemUrl = listUrl[position]
//            holder.picture.let {
//                Glide.with(PetWelfareApplication.context)
//                    .load(GlideUrl(itemUrl, Repository.lazyHeaders))
//                    .into(it)
//            }
//        }
    }

}