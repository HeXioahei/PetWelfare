package com.example.petwelfare.ui.main.head

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.R


class ItemFollowFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_follow, container, false)
    }

}