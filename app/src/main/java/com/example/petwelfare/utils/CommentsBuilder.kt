package com.example.petwelfare.utils

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.petwelfare.R
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.KidComment
import com.example.petwelfare.ui.main.mine.MineActivity

object CommentsBuilder {

    // 创建父评论
    private fun onCreateParentCommentsList(list: MutableList<Comments>, context: Context, layoutInflater: LayoutInflater, commentsContainer: LinearLayoutCompat) {
        for (item in list) {
            val view = layoutInflater.inflate(R.layout.item_comments_parent, null, false)
            val respondBtn : ImageView = view.findViewById(R.id.respondBtn)
            respondBtn.setOnClickListener {
                writeComments("child", context)
            }
            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
            headImage.setOnClickListener {
                val intent = Intent(context, MineActivity::class.java)
                context.startActivity(intent)
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
                addKidComment(kidComment,item.kid_comments,index, context, layoutInflater)
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

            commentsContainer.addView(view)
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

    private fun cleanKidComments(viewGroup: LinearLayoutCompat) {
        viewGroup.removeAllViews()
    }

    private fun addKidComment(viewGroup: LinearLayoutCompat, list: MutableList<KidComment>, index: Int, context: Context, layoutInflater: LayoutInflater) {
        for (i in 0 until 2) {
            if(index + i >= list.size) {
                Toast.makeText(context,"已展示全部回复", Toast.LENGTH_SHORT).show()
                return
            }
            val item = list[index + i]
            val view = layoutInflater.inflate(R.layout.item_comments_kid, null, false)
            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
            headImage.setOnClickListener {
                val intent = Intent(context, MineActivity::class.java)
                context.startActivity(intent)
            }
            val username: TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val content: TextView = view.findViewById(R.id.content)
            content.text = item.comment

            username.text = item.username
            viewGroup.addView(view)
        }
    }

    private fun writeComments(type: String, context: Context) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        // 创建一个 EditText 视图
        val input = EditText(context)
        input.hint = "请输入评论"
        //input.setBackgroundResource(R.drawable.bg_input)
        alertDialogBuilder.setView(input)

        // 设置对话框的按钮
        alertDialogBuilder.setPositiveButton("发表") { _, _ ->
            val content = input.text.toString()
            //进行网络请求

        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }
        // 显示对话框
        alertDialogBuilder.show()
    }

}