package com.example.petwelfare.ui.main.mine.edit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.ActivityEditMyInfoBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.FileBuilder
import com.example.petwelfare.ui.begin.RestorePsdActivity
import com.example.petwelfare.ui.main.mine.MineViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

class EditMyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditMyInfoBinding

    private val viewModel1: EditMyInfoViewModel by viewModels()
    private val viewModel2: MineViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        val username = viewModel2.myDetail.username
        val address = viewModel2.myDetail.address
        val telephone = viewModel2.myDetail.telephone
        val personality = viewModel2.myDetail.personality

        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 修改头像
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

                    val headImageFile = FileBuilder.getImageFileFromUri(this, uri)
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
            showAlertDialog(username, "changeUsername")
        }

        // 修改密码
        binding.changePsd.setOnClickListener {
            val intent = Intent(this, RestorePsdActivity::class.java)
            startActivity(intent)
        }

        // 修改地址
        binding.changeAddress.setOnClickListener {
            showAlertDialog(address, "changeAddress")
        }

        // 修改电话
        binding.changeTelephone.setOnClickListener {
            showAlertDialog(telephone, "changeTelephone")
        }

        // 修改个性签名
        binding.changePersonality.setOnClickListener {
            showAlertDialog(personality, "changePersonality")
        }
    }

    private fun showAlertDialog(initText: String, type: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // 创建一个 EditText 视图
        val input = EditText(this)
        input.setText(initText)
        //input.setBackgroundResource(R.drawable.bg_input)
        alertDialogBuilder.setView(input)

        // 设置对话框的按钮
        alertDialogBuilder.setPositiveButton("确定") { _, _ ->
            val inputText = input.text.toString()
            val Authorization = Repository.Authorization
            when (type) {
                "changeUsername" -> {
                    val code = viewModel1.changeUsername(inputText, Authorization)
                    if (code == 200) {
                        Toast.makeText(this,"修改成功", Toast.LENGTH_SHORT).show()
                        viewModel2.setUsername(inputText)
                    } else {
                        Toast.makeText(this,"修改失败",Toast.LENGTH_SHORT).show()
                    }

                }

                "changeAddress" -> {
                    viewModel1.changeAddress(inputText, Authorization)
                    viewModel2.setAddress(inputText)
                }

                "changeTelephone" -> {
                    viewModel1.changeTelephone(inputText, Authorization)
                    viewModel2.setTelephone(inputText)
                }

                "changePersonality" -> {
                    viewModel1.changePersonality(inputText, Authorization)
                    viewModel2.setPersonality(inputText)
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