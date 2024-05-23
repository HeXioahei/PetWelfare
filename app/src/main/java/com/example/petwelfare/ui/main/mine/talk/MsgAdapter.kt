package com.example.petwelfare.ui.main.mine.talk

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.ItemMsgLeftBinding
import com.example.petwelfare.databinding.ItemMsgRightBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Msg

class MsgAdapter(private val msgList: MutableList<Msg>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    inner class LeftViewHolder(binding: ItemMsgLeftBinding) : RecyclerView.ViewHolder(binding.root) {
        val leftMsg: TextView = binding.leftMsg
    }

    inner class RightViewHolder(binding: ItemMsgRightBinding) : RecyclerView.ViewHolder(binding.root) {
        val rightMsg: TextView = binding.rightMsg
    }

    override fun getItemViewType(position: Int): Int {
        val msg = msgList[position]
        return if (msg.id == Repository.myId) {
            Msg.TYPE_SENT
        } else {
            Msg.TYPE_RECEIVE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Msg.TYPE_RECEIVE) {
            val binding = ItemMsgLeftBinding
                .inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
            val viewHolder = LeftViewHolder(binding)
            viewHolder
        } else {
            val binding = ItemMsgRightBinding
                .inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
            val viewHolder = RightViewHolder(binding)
            viewHolder
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val msg = msgList[position]
        when(holder) {
            is LeftViewHolder -> holder.leftMsg.text = msg.messgae
            is RightViewHolder -> holder.rightMsg.text = msg.messgae
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemCount() = msgList.size

}