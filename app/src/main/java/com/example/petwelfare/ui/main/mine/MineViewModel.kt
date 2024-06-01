package com.example.petwelfare.ui.main.mine

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.BaseResponse
import com.example.petwelfare.logic.model.UserDetail
import com.example.petwelfare.logic.network.PetWelfareNetwork
import com.example.petwelfare.ui.main.mine.edit.EditInfo
import com.example.petwelfare.ui.main.mine.item.collection.ItemCollectionFragment
import com.example.petwelfare.ui.main.mine.item.like.ItemLikesFragment
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineFragment
import com.example.petwelfare.ui.main.mine.item.pet.ItemPetFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

class MineViewModel : ViewModel() {

    var cursorList = listOf<AppCompatImageView>()

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
        }
    }

    fun delayAction(action: () -> Unit) {
        viewModelScope.launch {
            delay(5000)
            action.invoke()
        }
    }


    var editInfo = EditInfo()

    private val _changeResponse = MutableLiveData<BaseResponse>()
    val changeResponse : LiveData<BaseResponse> = _changeResponse

    fun changeHead(headImage: MultipartBody.Part, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeHead(headImage, Authorization)
            val code = PetWelfareNetwork.changeHead(headImage, Authorization).code
            if (code == 200) {
                Log.d("changeHead", "success")
            } else {
                Log.d("changeHead", "failure")
            }
        }
    }

    fun changeUsername(username: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeUsername(username, Authorization)
            val code = PetWelfareNetwork.changeUsername(username, Authorization).code
            if (code == 200) {
                Log.d("changeUsername", "success")
            } else {
                Log.d("changeUsername", "failure")
            }
        }
    }

    fun changeAddress(address: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeAddress(address, Authorization)
            val code = PetWelfareNetwork.changeAddress(address, Authorization).code
            if (code == 200) {
                Log.d("changeAddress", "success")
            } else {
                Log.d("changeAddress", "failure")
            }
        }
    }

    fun changeTelephone(telephone: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changeTelephone(telephone, Authorization)
            val code = PetWelfareNetwork.changeTelephone(telephone, Authorization).code
            if (code == 200) {
                Log.d("changeTelephone", "success")
            } else {
                Log.d("changeTelephone", "failure")
            }
        }
    }

    fun changePersonality(personality: String, Authorization: String) {
        viewModelScope.launch {
            _changeResponse.value = PetWelfareNetwork.changePersonality(personality, Authorization)
            val code = PetWelfareNetwork.changePersonality(personality, Authorization).code
            if (code == 200) {
                Log.d("changePersonality", "success")
            } else {
                Log.d("changePersonality", "failure")
            }
        }
    }


}





















//    /*这里的写法和列表页的写法有些不同，到时候测试的时候看看哪种比较好*/
//
//    private val infoLiveData = MutableLiveData<UserDetail>(UserDetail())   //进行网络请求时需要传入的参数
//
////    lateinit var myDetail: UserDetail
//    var myDetail: UserDetail = UserDetail()
//
//    // 若上述参数比如id变化，则会自动调用下用这个方法，进行网络请求（已经封装好在Repository类中了，
//    // 获取新的数据对象， 并将新获得的数据对象转化为当前的观察对象
//    var myDetailData = infoLiveData.switchMap { data ->
//        Repository.getUserInfo(data.id, Repository.Authorization)
//    }
//
//    // 可参考官方网站：
//    // https://developer.android.google.cn/topic/libraries/architecture/livedata?hl=zh-cn#transform_livedata
//
//    // 而上述说的参数比如id要怎么变化呢，那就得需要下面这个方法了，外界调用这个方法来使infoLiveData中的值改变
//
//    fun setUserId(id2: Long) {
//        infoLiveData.value?.id = id2
//    }
//    fun setUsername(username2: String) {
//        infoLiveData.value?.username = username2
//    }
//    fun setAddress(address2: String) {
//        infoLiveData.value?.address = address2
//    }
//    fun setTelephone(telephone2: String) {
//        infoLiveData.value?.telephone = telephone2
//    }
//    fun setPersonality(personality2: String) {
//        infoLiveData.value?.personality = personality2
//    }
