package com.example.petwelfare.ui.head

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentItemSquareBinding
import com.example.petwelfare.ui.MainActivity

class ItemSquareFragment(activity: MainActivity) : Fragment() {

    private lateinit var binding: FragmentItemSquareBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentItemSquareBinding.inflate(inflater, container, false)
        return binding.root
    }

}