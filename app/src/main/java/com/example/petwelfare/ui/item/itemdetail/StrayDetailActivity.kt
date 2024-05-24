package com.example.petwelfare.ui.item.itemdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityLossDetailBinding
import com.example.petwelfare.databinding.ActivityStrayDetailBinding
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.KidComment
import com.example.petwelfare.logic.model.TimeBuilder
import com.example.petwelfare.ui.main.mine.MineActivity

class StrayDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStrayDetailBinding
    private var comments : MutableList<Comments> = mutableListOf(
        Comments(),
        Comments(),
        Comments(),
        Comments()
    )
    private val viewModel : StrayDetailViewModel by viewModels()
    private var strayId = "-1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStrayDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        strayId = intent.getIntExtra("strayId", -1).toString()

        viewModel.getCommentsInStray(strayId)

        viewModel.commentsInStray.observe(this) { result->
            comments = result.data
        }

        // 写父评论
        binding.toWriteComments.setOnClickListener {
            writeComments(0, 1)
        }

        onCreateParentCommentsList(comments)
    }

    // 创建父评论
    private fun onCreateParentCommentsList(list: MutableList<Comments>) {
        for (item in list) {
            val view = layoutInflater.inflate(R.layout.item_comments_parent, null, false)
            val respondBtn : ImageView = view.findViewById(R.id.respondBtn)
            respondBtn.setOnClickListener {
                writeComments(item.cid, 2)
            }
            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
            headImage.setOnClickListener {
                val intent = Intent(this, MineActivity::class.java)
                startActivity(intent)
            }
            val username : TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val content : TextView = view.findViewById(R.id.content)
            content.text = item.comment
            val kidComment = view.findViewById<LinearLayoutCompat>(R.id.childCommentsList)
            //onCreateKidCommentsList(kidComment, item.kidComments)
            username.text = item.username

            var index = 0

            val expendBtn = view.findViewById<TextView>(R.id.expendBtn)
            expendBtn.setOnClickListener {
//                kidComment.layoutParams.height += 50
//                binding.commentsList.requestLayout()
                addKidComment(kidComment,item.kidComments,index)
                index += 2
            }

            val retractBtn = view.findViewById<TextView>(R.id.retractBtn)
            retractBtn.setOnClickListener {
//                val params = binding.commentsList.layoutParams
//                params.height = 0
//                kidComment.layoutParams = params
//                binding.commentsList.requestLayout()
                cleanKidComments(kidComment)
                index = 0
            }

            binding.commentsList.addView(view)
        }
    }

    private fun cleanKidComments(viewGroup: LinearLayoutCompat) {
        viewGroup.removeAllViews()
    }

    private fun addKidComment(viewGroup: LinearLayoutCompat, list: MutableList<KidComment>, index: Int) {
        for (i in 0 until 2) {
            if(index + i >= list.size) {
                Toast.makeText(this,"已展示全部回复", Toast.LENGTH_SHORT).show()
                return
            }
            val item = list[index + i]
            val view = layoutInflater.inflate(R.layout.item_comments_kid, null, false)
            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
            headImage.setOnClickListener {
                val intent = Intent(this, MineActivity::class.java)
                startActivity(intent)
            }
            val username: TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val content: TextView = view.findViewById(R.id.content)
            content.text = item.comment

            username.text = item.username
            viewGroup.addView(view)
        }
    }

//    private fun onCreateKidCommentsList(viewGroup: LinearLayoutCompat,list: MutableList<KidComment>) {
//        for (item in list) {
//            val view = layoutInflater.inflate(R.layout.item_comments_kid, null, false)
//            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
//            headImage.setOnClickListener {
//                val intent = Intent(this, com.example.petwelfare.ui.main.mine.MineActivity::class.java)
//                startActivity(intent)
//            }
//            val username : TextView = view.findViewById(R.id.usernameInParentComment)
//            username.text = item.username
//            val content : TextView = view.findViewById(R.id.content)
//            content.text = item.comment
//
//            username.text = item.username
//            viewGroup.addView(view)
//        }
//    }

    private fun writeComments(lastId: Int, level: Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // 创建一个 EditText 视图
        val input = EditText(this)
        input.hint = "请输入评论"
        //input.setBackgroundResource(R.drawable.bg_input)
        alertDialogBuilder.setView(input)

        // 设置对话框的按钮
        alertDialogBuilder.setPositiveButton("发表") { _, _ ->
            val time = TimeBuilder.getNowTime()
            val content = input.text.toString()
            //进行网络请求
            viewModel.writeComments(strayId, content, time, lastId, level)
        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }
        // 显示对话框
        alertDialogBuilder.show()
    }
}