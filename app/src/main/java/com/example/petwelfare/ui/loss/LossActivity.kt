package com.example.petwelfare.ui.loss

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.petwelfare.databinding.ActivityLossBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LossActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLossBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLossBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 获取当前定位
        if(ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 1)
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
                    getLocation()
                } else {
                    Toast.makeText(this,
                        "您未授予定位权限，已为您默认配置地址", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getLocation() {
        try {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    location?.let {
                        val latitude = it.latitude
                        val longitude = it.longitude
                        // 使用经纬度做你需要的操作
                    }
                }
                .addOnFailureListener { exception ->
                    // 处理获取位置失败的情况
                }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }


    }
}