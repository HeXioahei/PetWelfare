package com.example.petwelfare.ui.itemdetail.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityOrgDetailBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.itemdetail.viewmodel.OrgDetailViewModel

class OrgDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOrgDetailBinding

    private val viewModel : OrgDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrgDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.org.id = intent.getIntExtra("id", -1)
        viewModel.org.org_name = intent.getStringExtra("org_name").toString()
        viewModel.org.head_image = intent.getStringExtra("head_image").toString()
        viewModel.org.collect_nums = intent.getIntExtra("collect_nums", 0)
        viewModel.org.collect_status = intent.getIntExtra("collect_status", 0)
        viewModel.org.description = intent.getStringExtra("description").toString()
        viewModel.org.contract = intent.getStringExtra("contact").toString()


        binding.orgName.text = viewModel.org.org_name
        val headImageString = viewModel.org.head_image
        binding.headImage.let {
            Glide.with(this).load(GlideUrl(headImageString, Repository.lazyHeaders)).into(it)
        }
        binding.collectCount.text = viewModel.org.collect_nums.toString()
        binding.description.text = viewModel.org.description
        binding.contact.text = viewModel.org.contract

        if (viewModel.org.collect_status == 0) {
            binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
        } else {
            binding.followBtn.setBackgroundResource(R.drawable.img_followed)
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 点击进行关注
        binding.followBtn.setOnClickListener {
            viewModel.followOrg(viewModel.org.id.toString())
        }
        viewModel.followOrgResponse.observe(this) {
            Log.d("follow_status", viewModel.org.collect_status.toString())
            viewModel.org.collect_status = viewModel.org.collect_status xor 1
            if (viewModel.org.collect_status == 0) {
                binding.followBtn.setBackgroundResource(R.drawable.img_unfollowed_2)
            } else {
                binding.followBtn.setBackgroundResource(R.drawable.img_followed)
            }
        }
    }
}