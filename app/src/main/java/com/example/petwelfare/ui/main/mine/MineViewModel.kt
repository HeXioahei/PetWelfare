package com.example.petwelfare.ui.main.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.UserDetail

class MineViewModel : ViewModel() {

    /*这里的写法和列表页的写法有些不同，到时候测试的时候看看哪种比较好*/

    private val infoLiveData = MutableLiveData<UserDetail>(UserDetail())   //进行网络请求时需要传入的参数

//    lateinit var myDetail: UserDetail
    var myDetail: UserDetail = UserDetail()

    // 若上述参数比如id变化，则会自动调用下用这个方法，进行网络请求（已经封装好在Repository类中了，
    // 获取新的数据对象， 并将新获得的数据对象转化为当前的观察对象
    var myDetailData = infoLiveData.switchMap { data ->
        Repository.getUserInfo(data.id, Repository.Authorization)
    }

    // 可参考官方网站：
    // https://developer.android.google.cn/topic/libraries/architecture/livedata?hl=zh-cn#transform_livedata

    // 而上述说的参数比如id要怎么变化呢，那就得需要下面这个方法了，外界调用这个方法来使infoLiveData中的值改变

    fun setUserId(id2: Long) {
        infoLiveData.value?.id = id2
    }
    fun setUsername(username2: String) {
        infoLiveData.value?.username = username2
    }
    fun setAddress(address2: String) {
        infoLiveData.value?.address = address2
    }
    fun setTelephone(telephone2: String) {
        infoLiveData.value?.telephone = telephone2
    }
    fun setPersonality(personality2: String) {
        infoLiveData.value?.personality = personality2
    }
}