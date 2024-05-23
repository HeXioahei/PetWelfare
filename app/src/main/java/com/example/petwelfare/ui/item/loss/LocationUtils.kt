package com.example.petwelfare.ui.item.loss

import android.annotation.SuppressLint
import android.content.Context
import android.health.connect.datatypes.ExerciseRoute
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import com.google.android.gms.location.LocationListener
import java.io.IOException
import java.util.Locale

class LocationUtils(private val mContext: Context) {

        // 定位回调
//        private var mLocationCallBack: LocationCallBack? = null
//
//        // 定位管理实例
//        var mLocationManager: LocationManager? = null
//
//        /**
//         * 获取定位
//         * @param mLocationCallBack 定位回调
//         * @return
//         */
//        fun getLocation(mLocationCallBack: LocationCallBack?) {
//
//            this.mLocationCallBack = mLocationCallBack
//            if (mLocationCallBack == null) return
//            // 定位管理初始化
//            mLocationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            // 通过GPS定位
//            val gpsProvider = mLocationManager!!.getProvider(LocationManager.GPS_PROVIDER)
//            // 通过网络定位
//            val netProvider = mLocationManager!!.getProvider(LocationManager.NETWORK_PROVIDER)
//            // 优先考虑GPS定位，其次网络定位。
//            if (gpsProvider != null) {
//                gpsLocation()
//            } else if (netProvider != null) {
//                netWorkLocation()
//            } else {
//                mLocationCallBack.setLocation(null)
//            }
//        }
//
//        /**
//         * GPS定位
//         * @return
//         */
//        @SuppressLint("MissingPermission")
//        private fun gpsLocation() {
//            if (mLocationManager == null) mLocationManager =
//                mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            mLocationManager!!.requestLocationUpdates(
//                GPS_LOCATION, MIN_TIME, MIN_DISTANCE, mLocationListener
//            )
//        }
//
//        /**
//         * 网络定位
//         */
//        @SuppressLint("MissingPermission")
//        private fun netWorkLocation() {
//            if (mLocationManager == null) mLocationManager =
//                mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
//            mLocationManager!!.requestLocationUpdates(
//                NETWORK_LOCATION, MIN_TIME, MIN_DISTANCE, mLocationListener
//            )
//        }
//
//        // 定位监听
//        private val mLocationListener: LocationListener = object : LocationListener {
//            override fun onLocationChanged(location: Location) {
//                if (mLocationCallBack != null) {
//                    mLocationCallBack!!.setLocation(location)
//                }
//            }
//
//            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
//            override fun onProviderEnabled(provider: String) {}
//            override fun onProviderDisabled(provider: String) {
//                // 如果gps定位不可用,改用网络定位
//                if (provider == LocationManager.GPS_PROVIDER) {
//                    netWorkLocation()
//                }
//            }
//        }
//
//        /**
//         * 根据经纬度获取地址
//         * @param latitude 纬度
//         * @param longitude 经度
//         */
//        fun getAddress(latitude: Double, longitude: Double) {
//            // Address列表
//            var locationList: List<Address?>? = null
//            // 经纬度解码实例
//            val gc = Geocoder(mContext, Locale.getDefault())
//            try {
//                // 获取Address列表
//                locationList = gc.getFromLocation(latitude, longitude, MAX_RESULTS)
//                // 获取Address实例
//                val address = locationList[0]
//                if (mLocationCallBack != null) mLocationCallBack!!.setAddress(address)
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//
//        /**
//         * 获取地址周边信息
//         * @param
//         * @return
//         */
//        fun getAddressLine(address: Address): String {
//            var result = ""
//            var i = 0
//            while (address.getAddressLine(i) != null) {
//                val addressLine = address.getAddressLine(i)
//                result = result + addressLine
//                i++
//            }
//            return result
//        }
//
//        /**
//         * @className: LocationCallBack
//         * @classDescription: 定位回调
//         */
//        interface LocationCallBack {
//            fun setLocation(location: Location?)
//            fun setAddress(address: Address?)
//        }
//
//        companion object {
//            // GPS定位
//            private const val GPS_LOCATION = LocationManager.GPS_PROVIDER
//
//            // 网络定位
//            private const val NETWORK_LOCATION = LocationManager.NETWORK_PROVIDER
//
//            // 解码经纬度最大结果数目
//            private const val MAX_RESULTS = 1
//
//            // 时间更新间隔，单位：ms
//            private const val MIN_TIME: Long = 1000
//
//            // 位置刷新距离，单位：m
//            private const val MIN_DISTANCE = 0.01.toFloat()
//
//            // singleton
//            private var instance: LocationUtils? = null
//
//            /**
//             * singleton
//             * @param mContext 上下文
//             * @return
//             */
//            fun getInstance(mContext: Context): LocationUtils? {
//                if (instance == null) {
//                    instance = LocationUtils(mContext)
//                }
//                return instance
//            }
//        }
}