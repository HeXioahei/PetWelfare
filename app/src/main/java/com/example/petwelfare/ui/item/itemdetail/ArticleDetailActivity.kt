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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityArticleDetailBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.KidComment
import com.example.petwelfare.logic.model.TimeBuilder
import com.example.petwelfare.ui.main.mine.MineActivity

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArticleDetailBinding
//    private var comments : MutableList<Comments> = mutableListOf(Comments(),Comments(),Comments(),Comments())

    private val viewModel : ArticleDetailViewModel by viewModels()
//    private var articleId = "-1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // 将页面数据存进viewModel
        viewModel.article.id = intent.getIntExtra("articleId", -1)
        viewModel.article.user.username = intent.getStringExtra("username").toString()
        viewModel.article.user.headImage = intent.getStringExtra("headImage").toString()
        viewModel.article.user.id = intent.getLongExtra("userId", -1L)
        viewModel.article.text = intent.getStringExtra("text").toString()
        viewModel.article.time = intent.getStringExtra("time").toString()
        viewModel.article.collectNums = intent.getIntExtra("collectNums", -1)
        viewModel.article.collectStatus = intent.getIntExtra("collectStatus", -1)
        viewModel.article.likeNums = intent.getIntExtra("likeNums", -1)
        viewModel.article.likeStatus = intent.getIntExtra("likeStatus", -1)
        viewModel.article.commentNums = intent.getIntExtra("commentNums", -1)

        // 呈现数据
        binding.username.text = viewModel.article.user.username
        binding.articleText.text = viewModel.article.text
        binding.time.text = viewModel.article.time
        val myHeadImageString = viewModel.article.user.headImage
        val headImageGlideUrl = GlideUrl(myHeadImageString, Repository.lazyHeaders)
        binding.userHeadImage.let { Glide.with(this).load(headImageGlideUrl).into(it) }
        binding.collectCount.text = viewModel.article.collectNums.toString()
        binding.likeCount.text = viewModel.article.likeNums.toString()
        if (viewModel.article.collectStatus == 0) {
            binding.collectBtn.setBackgroundResource(R.drawable.img_uncollected)
        } else {
            binding.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
        }
        if (viewModel.article.likeStatus == 0) {
            binding.likeBtn.setBackgroundResource(R.drawable.img_unliked)
        } else {
            binding.likeBtn.setBackgroundResource(R.drawable.img_liked)
        }
        binding.commentsCount.text = viewModel.article.commentNums.toString()

        viewModel.getCommentsInArticle(viewModel.article.id.toString())

        viewModel.commentsInArticle.observe(this) { result->
            viewModel.comments = result.data
            onCreateParentCommentsList(viewModel.comments)
        }

        // 写父评论
        binding.toWriteComments.setOnClickListener {
            writeComments(0, 1)
        }

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
                intent.putExtra("userId", item.aid)
                startActivity(intent)
            }
            val username : TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val content : TextView = view.findViewById(R.id.content)
            content.text = item.comment
            val kidComment = view.findViewById<LinearLayoutCompat>(R.id.childCommentsList)
            username.text = item.username

            var index = 0

            val expendBtn = view.findViewById<TextView>(R.id.expendBtn)
            expendBtn.setOnClickListener {
                addKidComment(kidComment,item.kidComments,index)
                index += 2
            }

            val retractBtn = view.findViewById<TextView>(R.id.retractBtn)
            retractBtn.setOnClickListener {
                cleanKidComments(kidComment)
                index = 0
            }

            binding.commentsList.addView(view)
        }
    }

    private fun cleanKidComments(viewGroup: LinearLayoutCompat) {
        viewGroup.removeAllViews()
    }

    private fun addKidComment(viewGroup: LinearLayoutCompat,list: MutableList<KidComment>, index: Int) {
        for (i in 0 until 2) {
            if(index + i >= list.size) {
                Toast.makeText(this,"已展示全部回复",Toast.LENGTH_SHORT).show()
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

    private fun writeComments(lastId: Int, level :Int) {

        val alertDialogBuilder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.hint = "请输入评论"
//        input.setBackgroundResource(R.drawable.bg_input)
        alertDialogBuilder.setView(input)

        alertDialogBuilder.setPositiveButton("发表") { _, _ ->
            val content = input.text.toString()
            val time = TimeBuilder.getNowTime()
            //进行网络请求
            viewModel.writeComments(viewModel.article.id.toString(), content, time, lastId, level)
        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }
        // 显示对话框
        alertDialogBuilder.show()
    }

}