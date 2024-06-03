package com.example.petwelfare.ui.main.mine.pet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
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

        val userId = intent.getLongExtra("userId", -1)

        if (userId != Repository.myId) {
            binding.edit.visibility = View.GONE
        }

        PetViewModel.petInfo.pet_id = intent.getIntExtra("pet_id", -1)

        PetViewModel.petInfo.name = intent.getStringExtra("name") as String
        PetViewModel.petInfo.sex = intent.getStringExtra("sex") as String
        PetViewModel.petInfo.birthday = intent.getStringExtra("birthday") as String
        PetViewModel.petInfo.type = intent.getStringExtra("type") as String
        PetViewModel.petInfo.description = intent.getStringExtra("description") as String
        PetViewModel.petInfo.head_image = intent.getStringExtra("head_image") as String
        PetViewModel.petInfo.photos = intent.getStringArrayListExtra("photos") as ArrayList<String>

        binding.petName.text = PetViewModel.petInfo.name
        if (PetViewModel.petInfo.sex == "0") {
            binding.sex.setBackgroundResource(R.drawable.img_sex_male)
        } else {
            binding.sex.setBackgroundResource(R.drawable.img_sex_male)
        }
        binding.type.text = PetViewModel.petInfo.type
        binding.birthday.text = PetViewModel.petInfo.birthday
        binding.description.text = PetViewModel.petInfo.description

        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        var headImageString = PetViewModel.petInfo.head_image
        var headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        binding.petHeadImage.let { Glide.with(this).load(headImageGlideUrl).into(it) }

        // 照片墙
        /*                   */



        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 下拉刷新
        binding.swipeRefresh.setOnRefreshListener {
            binding.petName.text = PetViewModel.petInfo.name
            if (PetViewModel.petInfo.sex == "0") {
                binding.sex.setBackgroundResource(R.drawable.img_sex_male)
            } else {
                binding.sex.setBackgroundResource(R.drawable.img_sex_male)
            }
            binding.type.text = PetViewModel.petInfo.type
            binding.birthday.text = PetViewModel.petInfo.birthday
            binding.description.text = PetViewModel.petInfo.description

            headImageString = PetViewModel.petInfo.head_image
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