package com.example.petwelfare.ui.main.mine.pet.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityPetBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.adapter.listadapter.PhotosAdapter
import com.example.petwelfare.ui.main.mine.pet.viewmodel.PetViewModel

class PetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPetBinding

    val viewModel : PetViewModel by viewModels()

    val adapter = PhotosAdapter(PetViewModel.photosUri)

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
        PetViewModel.petTempInfo = PetViewModel.petInfo

        PetViewModel.photosUri.clear()
        for (item in PetViewModel.petInfo.photos) {
            PetViewModel.photosUri.add(GlideUrl(item, Repository.lazyHeaders).toStringUrl().toUri())
        }
        PetViewModel.tempPhotosUri.clear()
        PetViewModel.tempPhotosUri.addAll(PetViewModel.photosUri)

        val layoutManager = GridLayoutManager(this, 3)
        binding.pictureContainer.layoutManager = layoutManager
        binding.pictureContainer.adapter = adapter

        update()

        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 下拉刷新
        binding.swipeRefresh.setOnRefreshListener {
            update()
        }

        // 编辑
        binding.edit.setOnClickListener {
            val intent = Intent(this, EditPetInfoActivity::class.java)
            startActivity(intent)
        }

        PetViewModel.changeResponse.observe(this) {
            update()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun update() {
        binding.petName.text = PetViewModel.petInfo.name
        if (PetViewModel.petInfo.sex == "0") {
            binding.sex.setBackgroundResource(R.drawable.img_sex_female)
        } else {
            binding.sex.setBackgroundResource(R.drawable.img_sex_male)
        }
        binding.type.text = PetViewModel.petInfo.type
        binding.birthday.text = PetViewModel.petInfo.birthday
        binding.description.text = PetViewModel.petInfo.description

        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        val headImageString = PetViewModel.petInfo.head_image
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        binding.petHeadImage.let { Glide.with(this).load(headImageGlideUrl).into(it) }

        // 照片墙
        adapter.notifyDataSetChanged()

        binding.swipeRefresh.isRefreshing = false
    }
}