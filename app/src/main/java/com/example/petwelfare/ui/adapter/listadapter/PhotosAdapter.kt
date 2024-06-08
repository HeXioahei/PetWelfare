package com.example.petwelfare.ui.adapter.listadapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemPetPictureBinding

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
        return listUri.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val itemUri = listUri[position]
            holder.picture.let {
                Glide.with(PetWelfareApplication.context)
                    .load(itemUri)
                    .into(it)
            }
    }

}