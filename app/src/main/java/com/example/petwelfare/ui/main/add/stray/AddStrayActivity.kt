package com.example.petwelfare.ui.main.add.stray

import android.annotation.SuppressLint
import android.content.pm.PackageManager
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.bumptech.glide.Glide
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityAddStrayBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.utils.FileBuilder
import com.example.petwelfare.utils.TimeBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.time.LocalDateTime

class AddStrayActivity : AppCompatActivity(), AMapLocationListener {

    private lateinit var locationClient: AMapLocationClient

    private lateinit var binding : ActivityAddStrayBinding

    private var address = ""

    private var photosList = mutableListOf<@JvmSuppressWildcards Uri>()

    private var photosContainerList = mutableListOf<AppCompatImageView>()



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        ActivityCollector.addActivity(this)

        binding = ActivityAddStrayBinding.inflate(layoutInflater)

        val viewModel : AddStrayViewModel by viewModels()

        setContentView(binding.root)

        // 初始化定位客户端
        locationClient = AMapLocationClient(this)
        locationClient.setLocationListener(this)

        // 设置定位参数
        val option = AMapLocationClientOption()
        //option.isOnceLocation = true // 仅定位一次，默认为false
        option.isNeedAddress = true // 返回定位地址信息
        option.isLocationCacheEnable = true // 是否使用缓存定位，默认为true
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy // 设置定位模式为高精度模式

        //启动定位
        binding.getAddress.setOnClickListener {
            locationClient.setLocationOption(option)
        }

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
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }


        binding.addressContainer.visibility = View.GONE
        binding.timeContainer.visibility = View.GONE
        binding.address.setOnClickListener {
            binding.addressContainer.visibility = View.VISIBLE
            val province = binding.province.text.toString()
            val city = binding.city.text.toString()
            binding.confirmButton2.setOnClickListener {
                binding.address.text = "${province}省 ${city}市"
                binding.addressContainer.visibility = View.GONE
            }
        }
        binding.findTime.setOnClickListener {
            binding.timeContainer.visibility = View.VISIBLE
            var year = LocalDateTime.now().year
            var month = LocalDateTime.now().monthValue
            var day = LocalDateTime.now().dayOfMonth
            var hour = LocalDateTime.now().hour
            var minute = LocalDateTime.now().minute
            var second = LocalDateTime.now().second
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
                val time = "$year2-$month2-$day2    $hour2: $minute2: $second2"
                binding.findTime.text = time
                binding.timeContainer.visibility = View.GONE
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

            viewModel.sendStray(
                binding.address.text.toString(),
                TimeBuilder.getNowTime(),
                binding.description.text.toString(),
                Repository.Authorization,
                fileList
            )
        }

        viewModel.addStrayResponse.observe(this) {
            Toast.makeText(this,"发布成功", Toast.LENGTH_SHORT).show()
            Log.d("publishStray", "success")
            finish()
        }

        // 判断是否具有定位权限
        if(ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            locationClient.startLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationClient.startLocation()
                } else {
                    Toast.makeText(this,
                        "您未授予定位权限，已为您默认配置地址", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // 当启动发起定位的时候，就会开始监听定位结果，把结果回调到这个方法里
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                //可在其中解析amapLocation获取相应内容。
//                amapLocation.getProvince();//省信息
//                amapLocation.getCity();//城市信息
//                amapLocation.getDistrict();//城区信息
//                amapLocation.getStreet();//街道信息
//                amapLocation.getStreetNum();//街道门牌号信息
                address = amapLocation.province + amapLocation.city + amapLocation.district +
                        amapLocation.street + amapLocation.streetNum
                binding.address.setText(address)
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo
                )
            }
        }
    }

    private fun addPicture(uris: List<@JvmSuppressWildcards Uri>) {
//        val length = binding.photosContainer.horizontalFadingEdgeLength
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

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)

        // 销毁定位客户端
        locationClient.stopLocation()
        locationClient.onDestroy()
    }
}