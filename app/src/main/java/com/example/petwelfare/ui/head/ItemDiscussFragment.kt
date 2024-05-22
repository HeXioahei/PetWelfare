package com.example.petwelfare.ui.head

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.ui.MainActivity


class ItemDiscussFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_discuss, container, false)
    }
}