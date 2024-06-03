package com.example.petwelfare.ui.main.mine.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityEditPetInfoBinding
import com.example.petwelfare.databinding.DialogEditInfoBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.FileBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.internal.notify

class EditPetInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditPetInfoBinding

    private val viewModel : PetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.petName.setText(PetViewModel.petInfo.name)
        binding.sex.setText(PetViewModel.petInfo.sex)
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
        /*                   */

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
                    }

                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        binding.petHeadImage.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // 修改信息
        binding.petName.setOnClickListener {
            showAlertDialog(PetViewModel.petInfo.name, "changePetName")
        }
        binding.sex.setOnClickListener {
            showAlertDialog(PetViewModel.petInfo.sex, "changeSex")
        }
        binding.birthday.setOnClickListener {
            showAlertDialog(PetViewModel.petInfo.birthday, "changeBirthday")
        }
        binding.type.setOnClickListener {
            showAlertDialog(PetViewModel.petInfo.type, "changeType")
        }
        binding.description.setOnClickListener {
            showAlertDialog(PetViewModel.petInfo.description, "changeDescription")
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
                    PetViewModel.petInfo.name = inputText
                }

                "changeSex" -> {
                    viewModel.changeInfo("sex", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petInfo.sex = inputText
                }

                "changeType" -> {
                    viewModel.changeInfo("type", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petInfo.type = inputText
                }

                "changeBirthday" -> {
                    viewModel.changeInfo("birthday", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petInfo.birthday = inputText
                }
                "changeDescription" -> {
                    viewModel.changeInfo("description", Authorization, inputText, PetViewModel.petInfo.pet_id)
                    PetViewModel.petInfo.description = inputText
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

}