package com.example.petwelfare.ui.mine.item

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.logic.model.Pets
import com.example.petwelfare.logic.model.UserMostBrief

class ItemPetViewModel : ViewModel() {

    private val listLiveData = MutableLiveData<UserMostBrief>()

    lateinit var petList : Pets

    var petListData = listLiveData.switchMap { data->
        Repository.getPetsInfo(data.id)
    }

    fun setId (id2 : Long) {
        listLiveData.value?.id = id2
    }
}