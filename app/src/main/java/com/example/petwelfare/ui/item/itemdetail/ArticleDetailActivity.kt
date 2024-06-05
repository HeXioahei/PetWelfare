package com.example.petwelfare.ui.item.itemdetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityArticleDetailBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.KidComment
import com.example.petwelfare.utils.TimeBuilder
import com.example.petwelfare.ui.main.otheruser.OtherUserDetailActivity
import de.hdodenhof.circleimageview.CircleImageView

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArticleDetailBinding
//    private var comments : MutableList<Comments> = mutableListOf(Comments(),Comments(),Comments(),Comments())

    private val viewModel : ArticleDetailViewModel by viewModels()
//    private var articleId = "-1"

    private var respondIndex : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        // 将页面数据存进viewModel
        viewModel.article.id = intent.getIntExtra("articleId", -1)
        viewModel.article.user.username = intent.getStringExtra("username").toString()
        viewModel.article.user.head_image = intent.getStringExtra("headImage").toString()
        viewModel.article.user.id = intent.getLongExtra("userId", -1L)
        viewModel.article.text = intent.getStringExtra("text").toString()
        viewModel.article.time = intent.getStringExtra("time").toString()
        viewModel.article.user.follow_status = intent.getIntExtra("followStatus", 0)
        viewModel.article.collect_nums = intent.getIntExtra("collectNums", 0)
        viewModel.article.collect_status = intent.getIntExtra("collectStatus", 0)
        viewModel.article.like_nums = intent.getIntExtra("likeNums", 0)
        viewModel.article.like_status = intent.getIntExtra("likeStatus", 0)
        viewModel.article.comment_nums = intent.getIntExtra("commentNums", 0)
        viewModel.article.media = intent.getStringArrayListExtra("photos") as MutableList<String>

//        val photoList = listOf(binding.picture1, binding.picture2, binding.picture3)

        Log.d("follow_status", viewModel.article.user.follow_status.toString())

        // 呈现数据
        binding.username.text = viewModel.article.user.username
        Log.d("follow_status", viewModel.article.user.follow_status.toString())
        if (viewModel.article.user.follow_status == 0) {
            binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
        } else {
            binding.followBtn.setBackgroundResource(R.drawable.img_followed)
        }
        binding.time.text = viewModel.article.time
        val myHeadImageString = viewModel.article.user.head_image
        val headImageGlideUrl = GlideUrl(myHeadImageString, Repository.lazyHeaders)
        binding.userHeadImage.let { Glide.with(this).load(headImageGlideUrl).into(it) }

        binding.articleText.text = viewModel.article.text
        // 加载图片
        val photosContainer = listOf(binding.picture1, binding.picture2, binding.picture3)

        val size = if(viewModel.article.media.size > 3) 3 else viewModel.article.media.size

        for (i in 0 until size) {
            val photoGlideUrl = GlideUrl(viewModel.article.media[i])
            photosContainer[i].let { Glide.with(this).load(photoGlideUrl).into(it) }
        }

        binding.collectCount.text = viewModel.article.collect_nums.toString()
        binding.likeCount.text = viewModel.article.like_nums.toString()
        if (viewModel.article.collect_status == 0) {
            binding.collectBtn.setBackgroundResource(R.drawable.img_uncollected)
        } else {
            binding.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
        }
        if (viewModel.article.like_status == 0) {
            binding.likeBtn.setBackgroundResource(R.drawable.img_unliked)
        } else {
            binding.likeBtn.setBackgroundResource(R.drawable.img_liked)
        }
        binding.commentsCount.text = viewModel.article.comment_nums.toString()

        // 获取评论
        viewModel.getCommentsInArticle(viewModel.article.id.toString())

        // 呈现评论
        viewModel.commentsInArticle.observe(this) { result->
            viewModel.comments = result.data
            cleanComments()
            onCreateParentCommentsList(viewModel.comments)
        }

        // 写父评论
        binding.toWriteComments.setOnClickListener {
            writeComments(0, 1)
        }

        // 完成评论的发表，重新获取评论列表
        viewModel.writeCommentsResponse.observe(this) {
            viewModel.getCommentsInArticle(viewModel.article.id.toString())
            viewModel.article.comment_nums++
            binding.commentsCount.text = viewModel.article.comment_nums.toString()
        }

        // 点赞
        binding.likeBtn.setOnClickListener {
            viewModel.like(viewModel.article.id.toString())
        }
        // 收藏
        binding.collectBtn.setOnClickListener {
            viewModel.collect(viewModel.article.id.toString())
        }

        // 响应点赞
        viewModel.likeResponse.observe(this) {
            viewModel.article.like_status = viewModel.article.like_status xor 1
            if (viewModel.article.like_status == 0) {
                binding.likeBtn.setBackgroundResource(R.drawable.img_unliked)
                viewModel.article.like_nums--
            } else {
                binding.likeBtn.setBackgroundResource(R.drawable.img_liked)
                viewModel.article.like_nums++
            }
            binding.likeCount.text = viewModel.article.like_nums.toString()
        }
        // 响应收藏
        viewModel.collectResponse.observe(this) {
            viewModel.article.collect_status = viewModel.article.collect_status xor 1
            if (viewModel.article.collect_status == 0) {
                binding.collectBtn.setBackgroundResource(R.drawable.img_uncollected)
                viewModel.article.collect_nums--
            } else {
                binding.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
                viewModel.article.collect_nums++
            }
            binding.collectCount.text = viewModel.article.collect_nums.toString()
        }

        // 点击用户头像，进入用户详情页
        binding.userHeadImage.setOnClickListener {
            val intent = Intent(this, OtherUserDetailActivity::class.java)
            intent.putExtra("userId", viewModel.article.user.id)
            startActivity(intent)
        }

        // 点击进行关注
        binding.followBtn.setOnClickListener {
            viewModel.follow(viewModel.article.user.id.toString())
        }
        viewModel.followResponse.observe(this) {
            Log.d("follow_status", viewModel.article.user.follow_status.toString())
            viewModel.article.user.follow_status = viewModel.article.user.follow_status xor 1
            if (viewModel.article.user.follow_status == 0) {
                binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
            } else {
                binding.followBtn.setBackgroundResource(R.drawable.img_followed)
            }
        }

        // 返回
        binding.returnBtn.setOnClickListener {
            finish()
        }

    }

    private fun cleanComments() {
        binding.commentsList.removeAllViews()
    }

    // 创建父评论
    private fun onCreateParentCommentsList(list: MutableList<Comments>) {
        if (list.isNotEmpty()) {
            binding.commentsHint.visibility = View.INVISIBLE
        }
        for (i in list.indices) {
            val item = list[i]
            val view = layoutInflater.inflate(R.layout.item_comments_parent, binding.commentsList, false)
            val respondBtn : ImageView = view.findViewById(R.id.respondBtn)
            respondBtn.setOnClickListener {
                writeComments(item.cid, 2)
                respondIndex = i
            }
            val headImage = view.findViewById<CircleImageView>(R.id.userHeadImage)
            val glideUrl = GlideUrl(item.head_image, Repository.lazyHeaders)
            headImage.let { Glide.with(this).load(glideUrl).into(it) }
            headImage.setOnClickListener {
                val intent = Intent(this, OtherUserDetailActivity::class.java)
                intent.putExtra("userId", item.id)
                startActivity(intent)
            }
            val username : TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val time1 : TextView = view.findViewById(R.id.time1)
            time1.text = item.time
            val content : TextView = view.findViewById(R.id.content)
            content.text = item.comment

            val kidComment = view.findViewById<LinearLayoutCompat>(R.id.childCommentsList)

            var index = 0

            if (respondIndex == i) {
                index = item.kid_comments.size
                addKidCommentsAll(kidComment, item.kid_comments)
                respondIndex = 0
            }

            val expendBtn = view.findViewById<TextView>(R.id.expendBtn)
            expendBtn.setOnClickListener {
                addKidComment(kidComment,item.kid_comments,index)
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

    private fun addKidCommentsAll(viewGroup: LinearLayoutCompat,list: MutableList<KidComment>) {
        for (item in list) {
            val view = layoutInflater.inflate(R.layout.item_comments_kid, null, false)
            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
            val glideUrl = GlideUrl(item.head_image, Repository.lazyHeaders)
            headImage.let { Glide.with(this).load(glideUrl).into(it) }
            headImage.setOnClickListener {
                val intent = Intent(this, OtherUserDetailActivity::class.java)
                intent.putExtra("userId", item.aid)
                startActivity(intent)
            }
            val username: TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val time2 : TextView = view.findViewById(R.id.time2)
            time2.text = item.time
            val content: TextView = view.findViewById(R.id.content)
            content.text = item.comment

            username.text = item.username
            viewGroup.addView(view)
        }
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
            val glideUrl = GlideUrl(item.head_image, Repository.lazyHeaders)
            headImage.let { Glide.with(this).load(glideUrl).into(it) }
            headImage.setOnClickListener {
                val intent = Intent(this, OtherUserDetailActivity::class.java)
                intent.putExtra("userId", item.aid)
                startActivity(intent)
            }
            val username: TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val time2 : TextView = view.findViewById(R.id.time2)
            time2.text = item.time
            val content: TextView = view.findViewById(R.id.content)
            content.text = item.comment

            username.text = item.username
            viewGroup.addView(view)
        }
    }

    private fun writeComments(lastId: Int, level :Int) {

        val alertDialogBuilder = AlertDialog.Builder(this)
        val input = EditText(this)
        input.hint = if (level == 1) "请输入评论" else "请输入回复"
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

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

}