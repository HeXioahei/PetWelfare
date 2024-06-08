package com.example.petwelfare.ui.main.discovery.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentDiscoveryBinding
import com.example.petwelfare.ui.adapter.navadapter.DiscoveryNavAdapter
import com.example.petwelfare.ui.adapter.viewpageradapter.DiscoveryFragmentStateAdapter
import com.example.petwelfare.ui.main.discovery.search.activity.DiscoverySearchActivity
import com.example.petwelfare.ui.main.discovery.viewmodel.DiscoveryViewModel


@Suppress("DEPRECATION")
class DiscoveryFragment : Fragment(), AMapLocationListener {

    private lateinit var binding : FragmentDiscoveryBinding

    private val navItemList = listOf("走失", "流浪", "救助站", "寄养", "收养")

    companion object {
        var viewPagerCurrentPosition = 0
    }

    private lateinit var locationClient: AMapLocationClient

    private val viewModel : DiscoveryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDiscoveryBinding.inflate(inflater, container, false)

        val navAdapter = DiscoveryNavAdapter(navItemList, binding.viewPager)
        binding.navBar.adapter = navAdapter
        val layoutManager = LinearLayoutManager(PetWelfareApplication.context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        binding.navBar.layoutManager = layoutManager

        val viewpagerAdapter = DiscoveryFragmentStateAdapter(this, viewModel.viewPagerList)
        binding.viewPager.adapter = viewpagerAdapter

        // ViewPager2 的 item 变化，导航栏跟着变化，导航栏的光标也跟着变化
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("NotifyDataSetChanged")
            override fun onPageSelected(position: Int) {
                viewPagerCurrentPosition = position
                binding.navBar.scrollToPosition(position)
                navAdapter.notifyDataSetChanged()
            }
        })

        binding.toSearchBtn.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, DiscoverySearchActivity::class.java)
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }

        // 初始化定位客户端
        locationClient = AMapLocationClient(PetWelfareApplication.context)
        locationClient.setLocationListener(this)

        // 设置定位参数
        val option = AMapLocationClientOption()
//        option.isOnceLocation = true // 仅定位一次，默认为false
        option.isNeedAddress = true // 返回定位地址信息
        option.isLocationCacheEnable = true // 是否使用缓存定位，默认为true
        option.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy // 设置定位模式为高精度模式
        option.isNeedAddress = true  //设置是否返回地址信息（默认返回地址信息
        option.interval = 50000
        locationClient.setLocationOption(option)

        // 一开始进入页面就获取地址
        getLocation()

        // 重新获取地址
        binding.getAddressBtn.setOnClickListener {
            getLocation()
        }

        // 显示地址
        DiscoveryViewModel.addressLiveData.observe(viewLifecycleOwner) { result->
            Log.d("address", result)
            DiscoveryViewModel.address = result
            binding.address.text = DiscoveryViewModel.address
        }

        // 响应获取默认地址的请求的数据
        DiscoveryViewModel.addressDefaultLiveData.observe(viewLifecycleOwner) { result->
            DiscoveryViewModel.address = result.data.address
            binding.address.text = DiscoveryViewModel.address
            Toast.makeText(PetWelfareApplication.context,
                "您未授予定位权限，已为您默认配置地址", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun getLocation() {
        // 判断是否具有定位权限
        if(ContextCompat.checkSelfPermission(
                PetWelfareApplication.context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity as Activity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
            Log.d("locationClient.startLocation()1", "locationClient.startLocation()1")
        } else {
            Log.d("locationClient.startLocation()", "locationClient.startLocation()")
            locationClient.startLocation()
        }
    }

    @Deprecated("Deprecated in Java")
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
                    viewModel.getAddressDefault()
                }
            }
        }
    }

    // 当启动发起定位的时候，就会开始监听定位结果，把结果回调到这个方法里
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null) {
            if (amapLocation.errorCode == 0) {
                Log.d("location", "success")
                //可在其中解析amapLocation获取相应内容。
                Log.d("district", amapLocation.district)
                Log.d("province", amapLocation.province)
                Log.d("city", amapLocation.city)
                Log.d("street", amapLocation.street)
                Log.d("streetNum", amapLocation.streetNum)
                val addressDetail = amapLocation.province + amapLocation.city + amapLocation.district +
                        amapLocation.street + amapLocation.streetNum
                val address = amapLocation.city
                Log.d("address", address)
                DiscoveryViewModel.changeAddress(address)
                DiscoveryViewModel.changeAddressDetail(addressDetail)
            }else {
                Log.d("errorCode", amapLocation.errorCode.toString())
                when(amapLocation.errorCode) {
                    3-> {
                        Toast.makeText(PetWelfareApplication.context, "请对所连接网络进行全面检查", Toast.LENGTH_SHORT).show()
                        locationClient.stopLocation()
                    }
                }

                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.errorCode + ", errInfo:"
                        + amapLocation.errorInfo
                )
            }
            locationClient.stopLocation()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 销毁定位客户端
        locationClient.stopLocation()
        locationClient.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        locationClient.stopLocation()
    }
}