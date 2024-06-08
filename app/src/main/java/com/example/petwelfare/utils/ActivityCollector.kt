package com.example.petwelfare.utils

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.petwelfare.ui.begin.activity.BeginActivity
import com.example.petwelfare.ui.main.MainActivity
import com.example.petwelfare.ui.begin.activity.LoginActivity
import com.example.petwelfare.ui.begin.activity.LoadingActivity
import com.example.petwelfare.ui.main.mine.MineActivity
import com.example.petwelfare.ui.main.otheruser.activity.OtherUserDetailActivity

object ActivityCollector {
    private val activities = ArrayList<Activity> ()

    var beginActivity = BeginActivity()
    var loadingActivity = LoadingActivity()
    var mainActivity = MainActivity()
    var mineActivity = MineActivity()
    var otherUserActivity = OtherUserDetailActivity()

    fun addActivity(activity: Activity) {
        if(!activities.contains(activity)) {
            activities.add(activity)
        }
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun removeActivityUntilLogin() {
        var flag = 0
        for (activity2 in activities) {
            if (flag == 1) {
                if (!activity2.isFinishing) {
                    activity2.finish()
                    activities.remove(activity2)
                }
            }
            if (activity2 is LoginActivity) {
                flag = 1
            }
        }
    }

    fun removeActivityUntilBegin() {
        Log.d("activity", activities.toString())
        var flag = 0
        val activitiesTemp = mutableListOf<Activity>()
        activitiesTemp.addAll(activities)
        for (activity2 in activities) {
            Log.d("activity2", activities.toString())
            if (flag == 1) {
                Log.d("activity3", activities.toString())
                activitiesTemp.remove(activity2)
                Log.d("activity4", activities.toString())
                activity2.finish()
                Log.d("activity5", activities.toString())
                continue
            }
            if (activity2 is BeginActivity) {
                flag = 1
            }
        }
        activities.clear()
        activities.addAll(activitiesTemp)
        Log.d("activity6", activities.toString())
        val intent = Intent(beginActivity, LoginActivity::class.java)
        beginActivity.startActivity(intent)
    }

    fun removeAll() {
        val activitiesTemp = mutableListOf<Activity>()
        activitiesTemp.addAll(activities)
        for (activity2 in activities) {
            activitiesTemp.remove(activity2)
            activity2.finish()
        }
        activities.clear()
    }
}