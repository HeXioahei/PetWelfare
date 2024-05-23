package com.example.petwelfare.ui.adapter.listadapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ItemMailboxBinding
import com.google.android.material.textfield.TextInputEditText

class MailboxAdapter(private val list: List<String>, val editView : TextInputEditText, val container: NestedScrollView, val btn: AppCompatImageView) : RecyclerView.Adapter<MailboxAdapter.MyMailboxViewHolder>() {

    inner class MyMailboxViewHolder(binding: ItemMailboxBinding) : RecyclerView.ViewHolder(binding.root) {
        val mailboxText = binding.mailboxText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMailboxViewHolder {
        val binding = ItemMailboxBinding.inflate(LayoutInflater.from(PetWelfareApplication.context), parent, false)
        return MyMailboxViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: MyMailboxViewHolder, position: Int) {
        val item = list[position]
        holder.mailboxText.text = item
        holder.mailboxText.setOnClickListener {
            editView.setText(changeMailbox(editView.text.toString(), holder.mailboxText.text.toString()))
            container.visibility = View.INVISIBLE
            btn.background = PetWelfareApplication.context.getDrawable(R.drawable.btn_menu)
        }
    }

    private fun changeMailbox(text: String, mailbox: String) : String {
        val index = if(text.contains("@")) text.indexOf("@") else text.length
        return text.substring(0, index) + mailbox
    }
}