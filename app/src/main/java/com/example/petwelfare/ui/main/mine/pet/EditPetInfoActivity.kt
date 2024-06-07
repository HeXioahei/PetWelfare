package com.example.petwelfare.ui.main.mine.pet

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityEditPetInfoBinding
import com.example.petwelfare.databinding.DialogEditInfoBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.utils.FileBuilder
import com.example.petwelfare.ui.adapter.listadapter.PhotosAdapter
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class EditPetInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditPetInfoBinding

    private val viewModel : PetViewModel by viewModels()

    val adapter = PhotosAdapter(PetViewModel.photosUri)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        val layoutManager = GridLayoutManager(this, 3)
        binding.pictureContainer.layoutManager = layoutManager
        binding.pictureContainer.adapter = adapter

        update()

        binding.swipeRefresh.setOnRefreshListener {
            update()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }

        Log.d("id", PetViewModel.petInfo.pet_id.toString())

        // 修改头像
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

                    val headImageFile = FileBuilder.getImageFileFromUri(this, uri, 0)
                    val requestBody = headImageFile?.asRequestBody("head_image".toMediaTypeOrNull())
                    if (requestBody != null) {

                        val multipartBody = MultipartBody.Part.createFormData("head_image", headImageFile.name, requestBody) // 这里的name（”head_image“）必须和接口文档里定义的参数名字一样
                        viewModel.changeHead(PetViewModel.petInfo.pet_id, multipartBody, Repository.Authorization)
                        PetViewModel.petTempInfo.head_image = uri.toString()
                    }

                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        binding.petHeadImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.save.setOnClickListener {
            if (binding.petName.text.toString() != PetViewModel.petInfo.name) {
                viewModel.changeInfo("name", Repository.Authorization, binding.petName.text.toString(), PetViewModel.petInfo.pet_id)
                PetViewModel.petTempInfo.name = binding.petName.text.toString()
            }
            if (binding.type.text.toString() != PetViewModel.petInfo.type) {
                viewModel.changeInfo("type", Repository.Authorization, binding.type.text.toString(), PetViewModel.petInfo.pet_id)
                PetViewModel.petTempInfo.type = binding.type.text.toString()
            }
            if (binding.birthday.text.toString() != PetViewModel.petInfo.birthday) {
                var str = ""
                for (i in binding.birthday.text.toString()) {
                    if (i != '-') {
                        str += i
                    }
                }
                viewModel.changeInfo("birthday", Repository.Authorization, str, PetViewModel.petInfo.pet_id)
                PetViewModel.petTempInfo.birthday = binding.birthday.text.toString()
            }
            if (binding.description.text.toString() != PetViewModel.petInfo.description) {
                Log.d("a", PetViewModel.petInfo.pet_id.toString())
                Log.d("a", binding.description.text.toString())
                viewModel.changeInfo("description", Repository.Authorization, binding.description.text.toString(), PetViewModel.petInfo.pet_id)
                PetViewModel.petTempInfo.description = binding.description.text.toString()
            }
            val sex2 : String = if (binding.sex.text.toString() == "雌") {
                "0"
            } else {
                "1"
            }
            if (PetViewModel.petInfo.sex != sex2) {
                viewModel.changeInfo("sex", Repository.Authorization, sex2, PetViewModel.petInfo.pet_id)
                PetViewModel.petTempInfo.sex = sex2
            }
        }

//        // 修改信息
//        binding.petName.setOnClickListener {
//            showAlertDialog(PetViewModel.petInfo.name, "changePetName")
//        }
//        binding.sex.setOnClickListener {
//            showAlertDialog(PetViewModel.petInfo.sex, "changeSex")
//        }
//        binding.birthday.setOnClickListener {
//            showAlertDialog(PetViewModel.petInfo.birthday, "changeBirthday")
//        }
//        binding.type.setOnClickListener {
//            showAlertDialog(PetViewModel.petInfo.type, "changeType")
//        }
//        binding.description.setOnClickListener {
//            showAlertDialog(PetViewModel.petInfo.description, "changeDescription")
//        }

        // 添加照片
        val pickMedia2 =
            registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(3)) { uris ->
                if (uris.isNotEmpty()) {
                    Log.d("PhotoPicker", "Selected URI: ${uris.size}")

                    val fileList = mutableListOf<MultipartBody.Part>()
                    for (i in uris.indices) {
                        PetViewModel.tempPhotosUri.add(uris[i])
                        val file = FileBuilder.getImageFileFromUri(this, uris[i], i+1) as File
                        val requestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                        val multipartBody = MultipartBody.Part.createFormData("photos", file.name, requestBody)
                        fileList.add(multipartBody)
                    }
                    Log.d("fileList", fileList.toString())
                    viewModel.addPicture(PetViewModel.petInfo.pet_id, fileList)

                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }

        binding.addPicture.setOnClickListener {
            pickMedia2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // 修改成功
        PetViewModel.changeResponse.observe(this) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show()
            PetViewModel.petInfo = PetViewModel.petTempInfo
            PetViewModel.photosUri.clear()
            Log.d("tempPhotosUri", PetViewModel.tempPhotosUri.toString())
            PetViewModel.photosUri.addAll(PetViewModel.tempPhotosUri)
            Log.d("photosUri", PetViewModel.photosUri.toString())
            update()
        }
    }

    private fun showAlertDialog(initText: String, type: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)

//        // 创建一个 EditText 视图
//        val input = EditText(this)
//        input.setText(initText)
//        input.setBackgroundResource(R.drawable.bg_input)
//        alertDialogBuilder.setView(input)

        val binding : DialogEditInfoBinding = DialogEditInfoBinding.inflate(layoutInflater)
        alertDialogBuilder.setView(binding.root)

        binding.appCompatEditText.setText(initText)

        // 设置对话框的按钮
        alertDialogBuilder.setPositiveButton("确定") { _, _ ->
            val inputText = binding.appCompatEditText.text.toString()
            val Authorization = Repository.Authorization
            when (type) {
                "changePetname" -> {
                    viewModel.changeInfo("name", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petTempInfo.name = inputText
                }

                "changeSex" -> {
                    viewModel.changeInfo("sex", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petTempInfo.sex = inputText
                }

                "changeType" -> {
                    viewModel.changeInfo("type", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petTempInfo.type = inputText
                }

                "changeBirthday" -> {
                    viewModel.changeInfo("birthday", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petTempInfo.birthday = inputText
                }
                "changeDescription" -> {
                    viewModel.changeInfo("description", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petTempInfo.description = inputText
                }
            }
        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }

        // 显示对话框
        alertDialogBuilder.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun update() {
        binding.petName.setText(PetViewModel.petInfo.name)
        if (PetViewModel.petInfo.sex == "0") {
            binding.sex.setText("雌")
        } else {
            binding.sex.setText("雄")
        }
        binding.type.setText(PetViewModel.petInfo.type)
        binding.birthday.setText(PetViewModel.petInfo.birthday)
        binding.description.setText(PetViewModel.petInfo.description)
        val lazyHeaders = LazyHeaders.Builder()
            .addHeader("Authorization", Repository.Authorization)
            .build()
        val headImageString = PetViewModel.petInfo.head_image
        val headImageGlideUrl = GlideUrl(headImageString, lazyHeaders)
        binding.petHeadImage.let { Glide.with(this).load(headImageGlideUrl).into(it) }

        // 照片墙
        adapter.notifyDataSetChanged()
    }

}