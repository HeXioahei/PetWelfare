package com.example.petwelfare.ui.mine.talk

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.databinding.ActivityTalkBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Msg
import com.example.petwelfare.logic.model.TimeBuilder

class TalkActivity : AppCompatActivity() {

    lateinit var binding : ActivityTalkBinding
    private var adapter: MsgAdapter? =null
    private val msgList: MutableList<Msg> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTalkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        adapter = MsgAdapter(msgList)
        binding.recyclerView.adapter = adapter

        binding.send.setOnClickListener {
            val content = binding.edit.text.toString()
            val time = TimeBuilder.getNowTime()   // 这样设置
            if (content.isNotEmpty()) {
                val msg = Msg(Repository.myId, content, time)
                msgList.add(msg)
                adapter?.notifyItemInserted(msgList.size - 1)
                binding.recyclerView.scrollToPosition(msgList.size - 1)
                binding.edit.setText("")
            }
        }
    }

    private fun initMsg() {

    }
}