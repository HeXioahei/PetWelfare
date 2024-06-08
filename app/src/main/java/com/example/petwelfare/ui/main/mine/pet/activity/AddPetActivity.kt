package com.example.petwelfare.ui.main.mine.pet.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityAddPetBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.main.mine.pet.viewmodel.AddPetViewModel
import com.example.petwelfare.utils.FileBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDateTime

class AddPetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddPetBinding

    val viewModel : AddPetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        var headImageUri = " ".toUri()

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")
                    headImageUri = uri
                    binding.headImage.let { Glide.with(this).load(headImageUri).into(it) }
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        binding.headImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.menuContainer.visibility = View.GONE
        binding.timeContainer.visibility = View.GONE

        binding.sex.setOnClickListener {
            binding.menuContainer.visibility = View.VISIBLE
            binding.female.setOnClickListener {
                binding.sex.text = binding.female.text.toString()
                binding.menuContainer.visibility = View.GONE
            }
            binding.male.setOnClickListener {
                binding.sex.text = binding.male.text.toString()
                binding.menuContainer.visibility = View.GONE
            }
        }

        binding.birthday.setOnClickListener {
            binding.timeContainer.visibility = View.VISIBLE
            val year = LocalDateTime.now().year
            val month = LocalDateTime.now().monthValue
            val day = LocalDateTime.now().dayOfMonth
            binding.year.setText(year.toString())
            binding.month.setText(month.toString())
            binding.day.setText(day.toString())

            binding.confirmButton.setOnClickListener {
                var year2: String
                var month2: String
                var day2: String
                binding.year.text.toString().toInt().apply { year2 = this.toString() }
                binding.month.text.toString().toInt()
                    .apply { month2 = if (this < 9) "0$this" else this.toString() }
                binding.day.text.toString().toInt()
                    .apply { day2 = if (this < 9) "0$this" else this.toString() }
                val time = "$year2-$month2-$day2"
                binding.birthday.text = time
                binding.timeContainer.visibility = View.GONE
            }

            binding.publishBtn.setOnClickListener {

                if (headImageUri == " ".toUri()) {
                    Toast.makeText(this, "请选择宠物照片作为头像", Toast.LENGTH_SHORT).show()
                } else {
                    val file = FileBuilder.getImageFileFromUri(this, headImageUri, 0) as File
                    val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                    val multipartBody =
                        MultipartBody.Part.createFormData("head_image", file.name, requestBody)

                    val sex2 = binding.sex.text.toString().let { if (it=="雌") "0" else "1" }

                    viewModel.addPets(
                        binding.name.text.toString(),
                        sex2,
                        binding.type.text.toString(),
                        binding.birthday.text.toString(),
                        binding.description.text.toString(),
                        Repository.Authorization,
                        multipartBody
                    )
                }
            }

            viewModel.addPetsResponse.observe(this) {
                Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show()
                Log.d("publishPet", "success")
                finish()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

}