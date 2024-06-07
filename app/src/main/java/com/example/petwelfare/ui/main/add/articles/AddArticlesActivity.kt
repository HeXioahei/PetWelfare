package com.example.petwelfare.ui.main.add.articles

import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import com.example.petwelfare.databinding.ActivityAddArticlesBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.utils.FileBuilder
import com.example.petwelfare.utils.TimeBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddArticlesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddArticlesBinding

    private var photosList = mutableListOf<@JvmSuppressWildcards Uri>()

    private var photosContainerList = mutableListOf<AppCompatImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()   // 使其紧贴着手机最上面

        ActivityCollector.addActivity(this)

        binding = ActivityAddArticlesBinding.inflate(layoutInflater)

        val viewModel : AddArticlesViewModel by viewModels()

        setContentView(binding.root)

//        val fileMap = mutableMapOf<String, MultipartBody.Part>()
//        val requestBodyList = mutableListOf<RequestBody>()
        val fileList = mutableListOf<MultipartBody.Part>()

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
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.publishBtn.setOnClickListener {

            for (i in 0 until photosList.size) {
                Log.d("i", i.toString())
                Log.d("uri", photosList[i].toString())
                val file = FileBuilder.getImageFileFromUri(this, photosList[i], i+1) as File
                Log.d("file", file.toString())
                val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                Log.d("requestBody", requestBody.toString())
                val multipartBody = MultipartBody.Part.createFormData("photo_list", file.name, requestBody)
                Log.d("file.name", file.name)
                Log.d("multipartBody", multipartBody.toString())
                fileList.add(multipartBody)
            }

            Log.d("fileList", fileList.toString())
            Log.d("photoList", photosList.toString())
            Log.d("time", TimeBuilder.getNowTime())
            viewModel.writeArticle(
                TimeBuilder.getNowTime(),
                binding.content.text.toString(),
                Repository.Authorization,
                fileList
            )

        }

        viewModel.addArticlesResponse.observe(this) { result->
            Log.d("addArticlesResponse", result.toString())
            val code = result.code
            if(code == 200) {
                Toast.makeText(this,"发布成功", Toast.LENGTH_SHORT).show()
                Log.d("publishArticle", "success")
                finish()
            } else {
                Toast.makeText(this,"发布失败", Toast.LENGTH_SHORT).show()
                Log.d("publishArticle", "failed")
            }
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