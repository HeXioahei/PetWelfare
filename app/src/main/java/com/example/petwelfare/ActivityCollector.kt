package com.example.petwelfare

import android.app.Activity
import com.example.petwelfare.ui.begin.LoginActivity

object ActivityCollector {
    private val activities = ArrayList<Activity> ()

    fun addActivity(activity: Activity) {
        if(!activities.contains(activity)) {
            activities.add(activity)
        }
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun removeActivityAfterLogin() {
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
}