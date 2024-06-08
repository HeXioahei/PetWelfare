package com.example.petwelfare.ui.itemdetail.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityLossDetailBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.KidComment
import com.example.petwelfare.utils.TimeBuilder
import com.example.petwelfare.ui.adapter.viewpageradapter.ViewPagerAdapter
import com.example.petwelfare.ui.itemdetail.viewmodel.LossDetailViewModel
import com.example.petwelfare.ui.main.otheruser.activity.OtherUserDetailActivity

class LossDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLossDetailBinding
    private val viewModel : LossDetailViewModel by viewModels()

    private var respondIndex : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLossDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.loss.id = intent.getIntExtra("lossId", -1)
        viewModel.loss.name = intent.getStringExtra("name").toString()
        viewModel.loss.type = intent.getStringExtra("type").toString()
        viewModel.loss.address = intent.getStringExtra("address").toString()
        viewModel.loss.lost_time = intent.getStringExtra("lostTime").toString()
        viewModel.loss.sex = intent.getIntExtra("sex", 0)
        viewModel.loss.contact = intent.getStringExtra("contact").toString()
        viewModel.loss.description = intent.getStringExtra("description").toString()
        viewModel.loss.photos = intent.getStringArrayListExtra("photos") as MutableList<String>

        viewModel.loss.user.username = intent.getStringExtra("username").toString()
        viewModel.loss.user.id = intent.getLongExtra("userId", -1L)
        viewModel.loss.user.head_image = intent.getStringExtra("headImage").toString()
        viewModel.loss.send_time = intent.getStringExtra("time").toString()
        viewModel.loss.user.follow_status = intent.getIntExtra("followStatus", 0)

        viewModel.loss.collect_nums = intent.getIntExtra("collectNums", 0)
        viewModel.loss.collect_status = intent.getIntExtra("collectStatus", 0)
        viewModel.loss.comment_nums = intent.getIntExtra("commentNums", 0)

        binding.username.text = viewModel.loss.user.username
        val userHeadImageGlideUrl = GlideUrl(viewModel.loss.user.head_image, Repository.lazyHeaders)
        binding.headImage.let { Glide.with(PetWelfareApplication.context).load(userHeadImageGlideUrl).into(it) }
        binding.time.text = viewModel.loss.send_time

        if (viewModel.loss.user.follow_status == 0) {
            binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
        } else {
            binding.followBtn.setBackgroundResource(R.drawable.img_followed)
        }

        binding.name.text = viewModel.loss.name
        if (viewModel.loss.sex == 0) {
            binding.sex.setBackgroundResource(R.drawable.img_sex_female)
        } else {
            binding.sex.setBackgroundResource(R.drawable.img_sex_male)
        }
        binding.type.text = viewModel.loss.type
        binding.address.text = viewModel.loss.address
        binding.lossTime.text = viewModel.loss.lost_time
        binding.contact.text = viewModel.loss.contact
        binding.description.text = viewModel.loss.description

        if (viewModel.loss.collect_status == 0) {
            binding.collectBtn.setBackgroundResource(R.drawable.img_uncollected)
        } else {
            binding.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
        }
        binding.collectCount.text = viewModel.loss.collect_nums.toString()
        binding.commentsCount.text = viewModel.loss.comment_nums.toString()

        // 呈现照片
        val viewPagerAdapter = ViewPagerAdapter(viewModel.loss.photos)
        binding.photosContainer.adapter = viewPagerAdapter


        // 获取评论列表
        viewModel.getCommentsInLoss(viewModel.loss.id.toString())

        // 响应评论数据并呈现
        viewModel.commentsInLoss.observe(this) { result->
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
            viewModel.getCommentsInLoss(viewModel.loss.id.toString())
            viewModel.loss.comment_nums++
            binding.commentsCount.text = viewModel.loss.comment_nums.toString()
        }

        // 收藏
        binding.collectBtn.setOnClickListener {
            viewModel.collect(viewModel.loss.id.toString())
        }

        // 响应收藏
        viewModel.collectResponse.observe(this) {
            viewModel.loss.collect_status = viewModel.loss.collect_status xor 1
            if (viewModel.loss.collect_status == 0) {
                binding.collectBtn.setBackgroundResource(R.drawable.img_uncollected)
                viewModel.loss.collect_nums--
            } else {
                binding.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
                viewModel.loss.collect_nums++
            }
            binding.collectCount.text = viewModel.loss.collect_nums.toString()
        }

        // 点击用户头像，进入用户详情页
        binding.headImage.setOnClickListener {
            val intent = Intent(this, OtherUserDetailActivity::class.java)
            intent.putExtra("userId", viewModel.loss.user.id)
            startActivity(intent)
        }

        // 关注
        binding.followBtn.setOnClickListener {
            viewModel.follow(viewModel.loss.user.id.toString())
        }
        viewModel.followResponse.observe(this) {
            viewModel.loss.user.follow_status = viewModel.loss.user.follow_status xor 1
            if (viewModel.loss.user.follow_status == 0) {
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
        for (i in list.indices) {
            val item = list[i]
            val view = layoutInflater.inflate(R.layout.item_comments_parent, null, false)
            val respondBtn : AppCompatTextView = view.findViewById(R.id.respondBtn)
            respondBtn.setOnClickListener {
                writeComments(item.cid, 2)
            }
            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
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
            username.text = item.username

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

    private fun addKidComment(viewGroup: LinearLayoutCompat, list: MutableList<KidComment>, index: Int) {
        for (i in 0 until 2) {
            if(index + i >= list.size) {
                Toast.makeText(this,"已展示全部回复", Toast.LENGTH_SHORT).show()
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
            viewModel.writeComments(viewModel.loss.id.toString(), content, time, lastId, level)
        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }
        // 显示对话框
        alertDialogBuilder.show()
    }
}