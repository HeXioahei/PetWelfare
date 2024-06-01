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
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityStrayDetailBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Comments
import com.example.petwelfare.logic.model.KidComment
import com.example.petwelfare.logic.model.TimeBuilder
import com.example.petwelfare.ui.adapter.viewpageradapter.ViewPagerAdapter
import com.example.petwelfare.ui.main.mine.MineActivity
import com.example.petwelfare.ui.main.otheruser.OtherUserDetailActivity

class StrayDetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStrayDetailBinding
    private val viewModel : StrayDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStrayDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.stray.id = intent.getIntExtra("strayId", -1)
        viewModel.stray.address = intent.getStringExtra("address").toString()
        viewModel.stray.photos = intent.getStringArrayListExtra("photos") as MutableList<String>

        viewModel.stray.user.username = intent.getStringExtra("username").toString()
        viewModel.stray.user.id = intent.getLongExtra("userId", -1L)
        viewModel.stray.user.head_image = intent.getStringExtra("headImage").toString()
        viewModel.stray.time = intent.getStringExtra("time").toString()
        viewModel.stray.user.follow_status = intent.getIntExtra("followStatus", 0)

        viewModel.stray.comments_nums = intent.getIntExtra("commentNums", 0)
        viewModel.stray.collect_nums = intent.getIntExtra("collectNums", 0)
        viewModel.stray.collect_status = intent.getIntExtra("collectStatus", 0)

        binding.username.text = viewModel.stray.user.username
        val userHeadImageGlideUrl = GlideUrl(viewModel.stray.user.head_image, Repository.lazyHeaders)
        binding.headImage.let { Glide.with(PetWelfareApplication.context).load(userHeadImageGlideUrl).into(it) }
        binding.sendTime.text = viewModel.stray.time
        binding.findTime.text = viewModel.stray.time
        binding.address.text = viewModel.stray.address
        binding.description.text = viewModel.stray.description

        if (viewModel.stray.user.follow_status == 0) {
            binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
        } else {
            binding.followBtn.setBackgroundResource(R.drawable.img_followed)
        }

        if (viewModel.stray.collect_status == 0) {
            binding.collectBtn.setBackgroundResource(R.drawable.img_uncollected_3)
        } else {
            binding.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
        }
        binding.collectCount.text = viewModel.stray.collect_nums.toString()
        binding.commentsCount.text = viewModel.stray.comments_nums.toString()

        // 呈现照片
        val viewPagerAdapter = ViewPagerAdapter(viewModel.stray.photos)
        binding.photosContainer.adapter = viewPagerAdapter


        viewModel.getCommentsInStray(viewModel.stray.id.toString())

        viewModel.commentsInStray.observe(this) { result->
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
            viewModel.getCommentsInStray(viewModel.stray.id.toString())
            viewModel.stray.comments_nums++
            binding.commentsCount.text = viewModel.stray.comments_nums.toString()
        }

        // 收藏
        binding.collectBtn.setOnClickListener {
            viewModel.collect(viewModel.stray.id.toString())
        }

        // 响应收藏
        viewModel.collectResponse.observe(this) {
            viewModel.stray.collect_status = viewModel.stray.collect_status xor 1
            if (viewModel.stray.collect_status == 0) {
                binding.collectBtn.setBackgroundResource(R.drawable.img_uncollected)
                viewModel.stray.collect_nums--
            } else {
                binding.collectBtn.setBackgroundResource(R.drawable.img_collected_3)
                viewModel.stray.collect_nums++
            }
            binding.collectCount.text = viewModel.stray.collect_nums.toString()
        }

        // 点击用户头像，进入用户详情页
        binding.headImage.setOnClickListener {
            val intent = Intent(this, OtherUserDetailActivity::class.java)
            intent.putExtra("userId", viewModel.stray.user.id)
            startActivity(intent)
        }

        // 关注
        binding.followBtn.setOnClickListener {
            viewModel.follow(viewModel.stray.user.id.toString())
        }
        viewModel.followResponse.observe(this) {
            viewModel.stray.user.follow_status = viewModel.stray.user.follow_status xor 1
            if (viewModel.stray.user.follow_status == 0) {
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
        for (item in list) {
            val view = layoutInflater.inflate(R.layout.item_comments_parent, null, false)
            val respondBtn : ImageView = view.findViewById(R.id.respondBtn)
            respondBtn.setOnClickListener {
                writeComments(item.cid, 2)
            }
            val headImage = view.findViewById<ImageView>(R.id.userHeadImage)
            headImage.setOnClickListener {
                val intent = Intent(this, OtherUserDetailActivity::class.java)
                startActivity(intent)
            }
            val username : TextView = view.findViewById(R.id.usernameInParentComment)
            username.text = item.username
            val time1 : TextView = view.findViewById(R.id.time1)
            time1.text = item.time
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
                addKidComment(kidComment,item.kid_comments,index)
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
                val intent = Intent(this, OtherUserDetailActivity::class.java)
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
            viewModel.writeComments(viewModel.stray.id.toString(), content, time, lastId, level)
        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }
        // 显示对话框
        alertDialogBuilder.show()
    }
}