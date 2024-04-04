package com.example.petwelfare.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityEditMyInfoBinding
import com.example.petwelfare.logic.model.AllData
import com.example.petwelfare.logic.model.FileBuilder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditMyInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditMyInfoBinding

    private val viewModel: EditMyInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditMyInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = AllData.userDetail.username
        val address = AllData.userDetail.address
        val telephone = AllData.userDetail.telephone
        val personality = AllData.userDetail.personality

        // 修改头像
        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {
                    Log.d("PhotoPicker", "Selected URI: $uri")

                    val headImageFile = FileBuilder.getImageFileFromUri(this, uri)
                    val requestBody = headImageFile?.asRequestBody("head_image".toMediaTypeOrNull())
                    if (requestBody != null) {
                        val multipartBody = MultipartBody.Part.createFormData("head_image", headImageFile.name, requestBody) // 这里的name（”head_image“）必须和接口文档里定义的参数名字一样
                        viewModel.changeHead(multipartBody, PetWelfareApplication.Authorization)
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

    fun showAlertDialog(initText: String, type: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)

        // 创建一个 EditText 视图
        val input = EditText(this)
        input.setText(initText)
        //input.setBackgroundResource(R.drawable.bg_input)
        alertDialogBuilder.setView(input)

        // 设置对话框的按钮
        alertDialogBuilder.setPositiveButton("确定") { _, _ ->
            val inputText = input.text.toString()
            val Authorization = PetWelfareApplication.Authorization
            when (type) {
                "changeUsername" -> viewModel.changeUsername(inputText, Authorization)
                "changeAddress" -> viewModel.changeAddress(inputText, Authorization)
                "changeTelephone" -> viewModel.changeTelephone(inputText, Authorization)
                "changePersonality" -> viewModel.changePersonality(inputText, Authorization)
            }
        }
        alertDialogBuilder.setNegativeButton("取消") { dialog, _ ->
            // 用户点击了取消按钮，这里可以不做处理或者执行相应的逻辑
            dialog.dismiss()
        }

        // 显示对话框
        alertDialogBuilder.show()
    }

}