package com.example.petwelfare.ui.mine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.UserDetail

class MineViewModel : ViewModel() {



    private val idLiveData = MutableLiveData<IdAndAuthorization>()   //进行网络请求时需要传入的参数

    lateinit var myDetail: UserDetail
    // 若上述参数id变化，则会自动调用下用这个方法，进行网络请求（已经封装好在Repository类中了，
    // 获取新的数据对象， 并将新获得的数据对象转化为当前的观察对象
    val myDetailData = idLiveData.switchMap { data ->
        Repository.getUserInfo(data.id, data.Authorization)
    }
    // 可参考官方网站：
    // https://developer.android.google.cn/topic/libraries/architecture/livedata?hl=zh-cn#transform_livedata

    // 而上述说的id要怎么变化呢，那就得需要下面这个方法了，外界调用这个方法来使idLiveData中的值改变
    fun getUser(id: Long, Authorization: String) {
        idLiveData.value?.id = id          // value 即 getValue() 方法
        idLiveData.value?.Authorization = Authorization
    }
}

data class IdAndAuthorization(
    var id: Long,
    var Authorization: String
)