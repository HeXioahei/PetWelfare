package com.example.petwelfare.ui.item.articles

import android.app.ActionBar.LayoutParams
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.ImageViewCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityAddArticlesBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.FileBuilder
import com.example.petwelfare.logic.model.TimeBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddArticlesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddArticlesBinding

    private var photosList = mutableListOf<@JvmSuppressWildcards Uri>()

    private var photosContainerList = mutableListOf<AppCompatImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ActivityCollector.addActivity(this)

        binding = ActivityAddArticlesBinding.inflate(layoutInflater)

        val viewModel : AddArticlesViewModel by viewModels()

        setContentView(binding.root)

        val fileMap = mutableMapOf<String, RequestBody>()

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


        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.publishBtn.setOnClickListener {

            for (i in 0 until photosList.size) {
                val file = FileBuilder.getImageFileFromUri(this, photosList[i]) as File
                fileMap["file${i+1}"] = file.asRequestBody("file${i+1}".toMediaTypeOrNull())
            }

            val code = viewModel.writeArticle(
                TimeBuilder.getNowTime(),
                binding.content.text.toString(),
                Repository.Authorization,
                fileMap
            )
            if(code == 200) {
                Toast.makeText(this,"发布成功", Toast.LENGTH_SHORT).show()
                Log.d("publishArticle", "success")
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
        for (uri in uris) {
            if(photosList.size == 3) {
                Toast.makeText(this,"最多只能选择三个图像哦", Toast.LENGTH_SHORT).show()
                return
            }
            val imageView = AppCompatImageView(this)
            val imageViewParams = LinearLayout.LayoutParams(
                190,190
            ).apply {
                marginStart = 20
            }
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