package com.example.petwelfare.ui.main.mine.pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.ActivityPetBinding
import com.example.petwelfare.logic.Repository

class PetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPetBinding

    val viewModel : PetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        viewModel.petInfo.pet_id = intent.getIntExtra("pet_id", 0)

        viewModel.petInfo.name = intent.getStringExtra("name") as String
        viewModel.petInfo.sex = intent.getStringExtra("sex") as String
        viewModel.petInfo.birthday = intent.getStringExtra("birthday") as String
        viewModel.petInfo.type = intent.getStringExtra("type") as String
        viewModel.petInfo.description = intent.getStringExtra("description") as String
        viewModel.petInfo.head_image = intent.getStringExtra("head_image") as String
        viewModel.petInfo.photos = intent.getStringArrayListExtra("photos") as ArrayList<String>

        binding.petName.text = viewModel.petInfo.name
        binding.sex.text = viewModel.petInfo.sex
        binding.type.text = viewModel.petInfo.type
        binding.birthday.text = viewModel.petInfo.birthday
        binding.description.text = viewModel.petInfo.description

        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        var headImageString = viewModel.petInfo.head_image
        var headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        binding.petHeadImage.let { Glide.with(this).load(headImageGlideUrl).into(it) }

        // 照片墙
        /*                   */



        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 下拉刷新
        binding.swipeRefresh.setOnRefreshListener {
            binding.petName.text = viewModel.petInfo.name
            binding.sex.text = viewModel.petInfo.sex
            binding.type.text = viewModel.petInfo.type
            binding.birthday.text = viewModel.petInfo.birthday
            binding.description.text = viewModel.petInfo.description

            headImageString = viewModel.petInfo.head_image
            headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
            binding.petHeadImage.let { Glide.with(this).load(headImageGlideUrl).into(it) }

            // 照片墙
            /*                   */
        }

        // 编辑
        binding.edit.setOnClickListener {
            val intent = Intent(this, EditPetInfoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}