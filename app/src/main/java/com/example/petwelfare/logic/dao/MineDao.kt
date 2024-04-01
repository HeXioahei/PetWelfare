package com.example.petwelfare.logic.dao

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.petwelfare.logic.model.User

// dao 即 data access object , 数据访问对象

object MineDao {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_info")
    // 必须放在顶部

    // dataStore相关用法：
    // csdn博客：https://blog.csdn.net/zhaoyanjun6/article/details/127358235?ops_request_misc=&request_id=&biz_id=102&utm_term=DataStore&utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduweb~default-5-127358235.142^v100^pc_search_result_base1&spm=1018.2226.3001.4187
    // 官方文档：https://developer.android.google.cn/topic/libraries/architecture/datastore?hl=zh-cn#preferences-datastore

    fun saveUser(user: User) {

    }
}