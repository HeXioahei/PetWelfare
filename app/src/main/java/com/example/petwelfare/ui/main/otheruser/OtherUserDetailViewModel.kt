package com.example.petwelfare.ui.main.otheruser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.GetArticlesResponse
import com.example.petwelfare.logic.model.GetLossResponse
import com.example.petwelfare.logic.model.GetPetsInfoResponse
import com.example.petwelfare.logic.model.GetStrayResponse
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.launch

class OtherUserDetailViewModel : ViewModel() {

    var otherUserInfo = UserDetail()

    private val _mmyArticles = MutableLiveData<GetArticlesResponse>()
    val mmyArticles : LiveData<GetArticlesResponse> = _mmyArticles

    private val _mmyLoss = MutableLiveData<GetLossResponse>()
    val mmyLoss : LiveData<GetLossResponse> = _mmyLoss

    private val _mmyStray = MutableLiveData<GetStrayResponse>()
    val mmyStray : LiveData<GetStrayResponse> = _mmyStray

    private val _mmyPets = MutableLiveData<GetPetsInfoResponse>()
    val mmyPets : LiveData<GetPetsInfoResponse> = _mmyPets

    fun getMmyArticles(id: Long) {
        viewModelScope.launch {
            _mmyArticles.value = PetWelfareNetwork.getMyArticles(id, Repository.Authorization)
        }
    }

    fun getMmyLoss(id: Long) {
        viewModelScope.launch {
            _mmyLoss.value = PetWelfareNetwork.getMyLoss(id, Repository.Authorization)
        }
    }

    fun getMmyStray(id: Long) {
        viewModelScope.launch {
            _mmyStray.value = PetWelfareNetwork.getMyStray(id, Repository.Authorization)
        }
    }

    fun getMmyPetsInfo(id: Long) {
        viewModelScope.launch {
            _mmyPets.value = PetWelfareNetwork.getPetsInfo(id)
        }
    }


}