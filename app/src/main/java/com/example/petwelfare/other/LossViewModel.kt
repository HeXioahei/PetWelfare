package com.example.petwelfare.other

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Loss

class LossViewModel : ViewModel() {

    private val addressLiveData = MutableLiveData<String>()

    var lossList: MutableList<Loss> = mutableListOf()

    val lossListData = addressLiveData.switchMap { data ->
        Repository.getLoss(data, Repository.Authorization)
    }

    fun setAddressLiveData(address: String) {
        addressLiveData.value = address
    }

}