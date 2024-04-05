package com.example.petwelfare.ui.loss

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Loss

class LossViewModel : ViewModel() {

    private val addressLiveData = MutableLiveData<String>()

    var lossList: MutableList<Loss> = mutableListOf()

    val lossListData = addressLiveData.switchMap { data ->
        Repository.getLoss(data, PetWelfareApplication.Authorization)
    }

    fun setAddressLiveData(address: String) {
        addressLiveData.value = address
    }
}