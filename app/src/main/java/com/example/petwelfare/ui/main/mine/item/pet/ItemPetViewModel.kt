package com.example.petwelfare.ui.main.mine.item.pet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetPetsInfoResponse
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.logic.model.Pets
import com.example.petwelfare.logic.model.UserMostBrief
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class ItemPetViewModel : ViewModel() {

    var myPetList : MutableList<Pet> = mutableListOf(Pet(), Pet(), Pet(), Pet(), Pet(), Pet())

    private val _myPet = MutableLiveData<GetPetsInfoResponse>()
    val myPet: LiveData<GetPetsInfoResponse> = _myPet

    fun getMyPets(id: Long) {
        viewModelScope.launch {
            _myPet.value = PetWelfareNetwork.getPetsInfo(id)
        }
    }
}