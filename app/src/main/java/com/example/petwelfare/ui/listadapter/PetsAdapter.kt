package com.example.petwelfare.ui.listadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemPetBinding
import com.example.petwelfare.logic.model.Pet

class PetsAdapter (private val list: MutableList<Pet>) : RecyclerView.Adapter<PetsAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ItemPetBinding) : RecyclerView.ViewHolder(binding.root) {
        // 数据与视图绑定
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding  = ItemPetBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        val viewHolder = ViewHolder(binding)
        return viewHolder
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        //...进行数据的处理与呈现
    }
}