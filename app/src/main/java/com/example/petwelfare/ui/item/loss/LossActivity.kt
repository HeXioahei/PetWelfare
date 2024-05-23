package com.example.petwelfare.ui.item.loss

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.ActivityLossBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

// （妙啊）高德开放平台关于获取定位信息的官方文档：https://lbs.amap.com/api/android-location-sdk/guide/android-location/getlocation

class LossActivity : AppCompatActivity(), AMapLocationListener {

    private lateinit var binding : ActivityLossBinding

    private lateinit var locationClient: AMapLocationClient

    private val viewModel: LossViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLossBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        // 初始化定位客户端
        locationClient = AMapLocationClient(this)
        locationClient.setLocationListener(this)

        // 设置定位参数
        val option = AMapLocationClientOption()
        //option.isOnceLocation = true // 仅定位一次，默认为false
        option.isNeedAddress = true // 返回定位地址信息
        option.isLocationCacheEnable = true // 是否使用缓存定位，默认为true
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy // 设置定位模式为高精度模式
        locationClient.setLocationOption(option)

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

        viewModel.lossListData.observe(this) { result ->
            val lossList = result.getOrNull()
            if (lossList != null) {
                viewModel.lossList = lossList
                // 传入recyclerView的adapter进行呈现
            } else {
                Toast.makeText(this, "未能获取信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
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
                val address = amapLocation.province + amapLocation.city + amapLocation.district +
                        amapLocation.street + amapLocation.streetNum
                viewModel.setAddressLiveData(address)
            }else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)

        // 销毁定位客户端
        locationClient.stopLocation()
        locationClient.onDestroy()
    }
}