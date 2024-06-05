package com.example.petwelfare.ui.main.mine.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityEditMyInfoBinding
import com.example.petwelfare.databinding.DialogEditInfoBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.utils.FileBuilder
import com.example.petwelfare.ui.begin.RestorePsdActivity
import com.example.petwelfare.ui.main.mine.MineViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class EditMyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditMyInfoBinding

    private val viewModel1: MineViewModel by viewModels()
//    private val viewModel2: MineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        Log.d("username", MineViewModel.userDetail.username)
        binding.username.text = MineViewModel.userDetail.username
        binding.personality.text = MineViewModel.userDetail.personality
        binding.address.text = MineViewModel.userDetail.address
        binding.telephone.text = MineViewModel.userDetail.telephone

        val glideUrl = GlideUrl(
            MineViewModel.userDetail.head_image,
            LazyHeaders.Builder()
                .addHeader("Authorization", Repository.Authorization)
                .build()
        )
        binding.changeHead.let { Glide.with(this).load(glideUrl).into(it) }

        // 刷新数据
        MineViewModel.userDetailLiveData.observe(this) { result ->

            Log.d("userDetail", result.toString())
            MineViewModel.userDetail = result

            update()
        }

        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 修改头像
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

                    val headImageFile = FileBuilder.getImageFileFromUri(this, uri, 0)
                    val requestBody = headImageFile?.asRequestBody("head_image".toMediaTypeOrNull())
                    if (requestBody != null) {
                        val multipartBody = MultipartBody.Part.createFormData("head_image", headImageFile.name, requestBody) // 这里的name（”head_image“）必须和接口文档里定义的参数名字一样
                        viewModel1.changeHead(multipartBody, Repository.Authorization)
                    }

                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }
        binding.changeHead.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        // 修改用户名
        binding.changeUsername.setOnClickListener {
            showAlertDialog(binding.username.text.toString(), "changeUsername")
        }

        // 修改密码
        binding.changePsd.setOnClickListener {
            val intent = Intent(this, RestorePsdActivity::class.java)
            startActivity(intent)
        }

        // 修改地址
        binding.changeAddress.setOnClickListener {
            showAlertDialog(binding.address.text.toString(), "changeAddress")
        }

        // 修改电话
        binding.changeTelephone.setOnClickListener {
            showAlertDialog(binding.telephone.text.toString(), "changeTelephone")
        }

        // 修改个性签名
        binding.changePersonality.setOnClickListener {
            showAlertDialog(binding.personality.text.toString(), "changePersonality")
        }

        // 成功更改
        viewModel1.changeResponse.observe(this) {

            viewModel1.getUserDetail(Repository.myId)

            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show()
        }



    }

    private fun showAlertDialog(initText: String, type: String) {
        val alertDialog = AlertDialog.Builder(this).create()

        val binding : DialogEditInfoBinding = DialogEditInfoBinding.inflate(layoutInflater)
        alertDialog.setView(binding.root)

        binding.appCompatEditText.setText(initText)

        alertDialog.setCancelable(true)

        binding.changeBtn.setOnClickListener {
            val inputText = binding.appCompatEditText.text.toString()
            val Authorization = Repository.Authorization
            when (type) {
                "changeUsername" -> {
                    viewModel1.changeUsername(inputText, Authorization)
                    Log.d("username", inputText)
                }

                "changeAddress" -> {
                    viewModel1.changeAddress(inputText, Authorization)
//                    viewModel2.setAddress(inputText)
                }

                "changeTelephone" -> {
                    viewModel1.changeTelephone(inputText, Authorization)
//                    viewModel2.setTelephone(inputText)
                }

                "changePersonality" -> {
                    viewModel1.changePersonality(inputText, Authorization)
//                    viewModel2.setPersonality(inputText)
                }
            }

            alertDialog.dismiss()
        }

        // 显示对话框
        alertDialog.show()

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    private fun update() {

        binding.username.text = MineViewModel.userDetail.username
        binding.address.text = MineViewModel.userDetail.address
        binding.personality.text = MineViewModel.userDetail.personality
        binding.telephone.text = MineViewModel.userDetail.telephone

        // 设置头像
        val glideUrl2 = GlideUrl(
            MineViewModel.userDetail.head_image,
            LazyHeaders.Builder()
                .addHeader("Authorization", Repository.Authorization)
                .build()
        )
        binding.changeHead.let { Glide.with(this).load(glideUrl2).into(it) }
    }

}