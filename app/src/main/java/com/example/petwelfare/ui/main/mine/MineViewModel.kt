package com.example.petwelfare.ui.main.mine

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.main.mine.itemlist.collection.fragment.ItemCollectionFragment
import com.example.petwelfare.ui.main.mine.itemlist.like.ItemLikesFragment
import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.ItemMineFragment
import com.example.petwelfare.ui.main.mine.itemlist.pet.ItemPetFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MineViewModel : ViewModel() {

    var cursorList = listOf<AppCompatImageView>()
    var textList = listOf<AppCompatTextView>()

    val itemMineFragment = ItemMineFragment()
    val itemCollectionFragment = ItemCollectionFragment()
    val itemPetFragment = ItemPetFragment(Repository.myId)
    val itemLikesFragment = ItemLikesFragment()

    companion object {
        var userDetail = UserDetail()

        private val _userDetailLiveData = MutableLiveData<UserDetail>()
        val userDetailLiveData: LiveData<UserDetail> = _userDetailLiveData
    }

    fun getUserDetail(userId: Long) {
        viewModelScope.launch {
            _userDetailLiveData.value =
                PetWelfareNetwork.getUserInfo(userId, Repository.Authorization).data.author
            Log.d("userDetail", PetWelfareNetwork.getUserInfo(userId, Repository.Authorization).data.author.toString())
        }
    }

    fun delayAction(action: () -> Unit) {
        viewModelScope.launch {
            delay(5000)
            action.invoke()
        }
    }

    private val _changeResponse = MutableLiveData<BaseResponse>()
    val changeResponse : LiveData<BaseResponse> = _changeResponse

    private val _changeUsernameResponse = MutableLiveData<BaseResponse>()
    val changeUsernameResponse : LiveData<BaseResponse> = _changeUsernameResponse

    fun changeHead(headImage: MultipartBody.Part, Authorization: String) {
        viewModelScope.launch {
            val response = PetWelfareNetwork.changeHead(headImage, Authorization)
            _changeResponse.value = response
            if (response.code == 200) {
                Log.d("changeHead", "success")
            } else {
                Log.d("changeHead", "failure")
            }
        }
    }

    fun changeUsername(username: String, Authorization: String) {
        viewModelScope.launch {
            val response = PetWelfareNetwork.changeUsername(username, Authorization)
            _changeUsernameResponse.value = response
            Log.d("username", username)
            if (response.code == 200) {
                Log.d("changeUsername", "success")
            } else {
                Log.d("changeUsername", "failure")
            }
        }
    }

    fun changeAddress(address: String, Authorization: String) {
        viewModelScope.launch {
            val response = PetWelfareNetwork.changeAddress(address, Authorization)
            _changeResponse.value = response
            if (response.code == 200) {
                Log.d("changeAddress", "success")
            } else {
                Log.d("changeAddress", "failure")
            }
        }
    }

    fun changeTelephone(telephone: String, Authorization: String) {
        viewModelScope.launch {
            val response = PetWelfareNetwork.changeTelephone(telephone, Authorization)
            _changeResponse.value = response
            if (response.code == 200) {
                Log.d("changeTelephone", "success")
            } else {
                Log.d("changeTelephone", "failure")
            }
        }
    }

    fun changePersonality(personality: String, Authorization: String) {
        viewModelScope.launch {
            val response = PetWelfareNetwork.changePersonality(personality, Authorization)
            _changeResponse.value = response
            if (response.code == 200) {
                Log.d("changePersonality", "success")
            } else {
                Log.d("changePersonality", "failure")
            }
        }
    }
}
