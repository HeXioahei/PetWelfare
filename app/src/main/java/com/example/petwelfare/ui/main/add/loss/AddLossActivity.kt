package com.example.petwelfare.ui.main.add.loss

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityAddLossBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.utils.FileBuilder
import com.example.petwelfare.utils.TimeBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDateTime

class AddLossActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddLossBinding

    private var photosList = mutableListOf<@JvmSuppressWildcards Uri>()

    private var photosContainerList = mutableListOf<AppCompatImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()

        ActivityCollector.addActivity(this)

        binding = ActivityAddLossBinding.inflate(layoutInflater)

        val viewModel : AddLossViewModel by viewModels()

        setContentView(binding.root)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
                if (uris != null) {
                    Log.d("PhotoPicker", "Selected URI: ${uris.size}")
                    addPicture(uris)
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        binding.toPhotoPickerBtn.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        binding.timeContainer.visibility = View.GONE
        binding.lossTime.setOnClickListener {
            binding.timeContainer.visibility = View.VISIBLE
            val year = LocalDateTime.now().year
            val month = LocalDateTime.now().monthValue
            val day = LocalDateTime.now().dayOfMonth
            val hour = LocalDateTime.now().hour
            val minute = LocalDateTime.now().minute
            val second = LocalDateTime.now().second
            binding.year.setText(year.toString())
            binding.month.setText(month.toString())
            binding.day.setText(day.toString())
            binding.hour.setText(hour.toString())
            binding.minute.setText(minute.toString())
            binding.second.setText(second.toString())

            binding.confirmButton.setOnClickListener {
                var year2: String
                var month2: String
                var day2: String
                var hour2: String
                var minute2: String
                var second2: String
                binding.year.text.toString().toInt().apply { year2 = this.toString() }
                binding.month.text.toString().toInt().apply { month2 = if (this < 9) "0$this" else this.toString() }
                binding.day.text.toString().toInt().apply { day2 = if (this < 9) "0$this" else this.toString() }
                binding.hour.text.toString().toInt().apply { hour2 = if (this < 9) "0$this" else this.toString() }
                binding.minute.text.toString().toInt().apply { minute2 = if (this < 9) "0$this" else this.toString() }
                binding.second.text.toString().toInt().apply { second2 = if (this < 9) "0$this" else this.toString() }
                val time = "$year2-$month2-$day2   $hour2: $minute2: $second2"
                binding.lossTime.text = time
                binding.timeContainer.visibility = View.GONE
            }
        }

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

        binding.publishBtn.setOnClickListener {

            val fileList = mutableListOf<MultipartBody.Part>()
            for (i in 0 until photosList.size) {
                val file = FileBuilder.getImageFileFromUri(this, photosList[i], i+1) as File
                val requestBody = file.asRequestBody("multipart/form-data".toMediaType())
                val multipartBody = MultipartBody.Part.createFormData("photo_list", file.name, requestBody)
                fileList.add(multipartBody)
            }

            val sex2 = if (binding.sex.text == "雌") 0 else 1

            viewModel.sendLoss(
                binding.name.text.toString(),
                sex2,
                binding.type.text.toString(),
                binding.address.text.toString(),
                binding.contact.text.toString(),
                binding.lossTime.text.toString(),
                TimeBuilder.getNowTime(),
                binding.description.text.toString(),
                Repository.Authorization,
                fileList
            )
        }

        viewModel.sendLossResponse.observe(this) {
            Toast.makeText(this,"发表成功",Toast.LENGTH_SHORT).show()
            Log.d("publishLoss", "success")
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    private fun addPicture(uris: List<@JvmSuppressWildcards Uri>) {
        val length = binding.photosContainer.horizontalFadingEdgeLength
        for (uri in uris) {
            if(photosList.size == 3) {
                Toast.makeText(this,"最多只能选择三个图像哦", Toast.LENGTH_SHORT).show()
                return
            }
            val imageView = AppCompatImageView(this)
            val imageViewParams = LinearLayout.LayoutParams(250,250).apply { marginStart = 20 }
            imageView.setOnLongClickListener{
                removePhoto(imageView)
                true
            }
            binding.photosContainer.addView(imageView, imageViewParams)
            imageView.let { Glide.with(this).load(uri).into(it) }
            photosContainerList.add(imageView)
            photosList.add(uri)
        }
    }

    private fun removePhoto(view: AppCompatImageView) {

        var index = -1

        for (i in 0 until photosContainerList.size) {
            if (view == photosContainerList[i]) {
                index = i
            }
        }

        val alertDialog = AlertDialog.Builder(this)

        alertDialog.setMessage("是否要删除此图片")

        alertDialog.setPositiveButton("确定") { _, _ ->
            binding.photosContainer.removeView(photosContainerList[index])
            photosContainerList.removeAt(index)
            photosList.removeAt(index)
        }
        alertDialog.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }

        // 显示对话框
        alertDialog.show()
    }
}