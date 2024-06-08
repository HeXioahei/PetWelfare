package com.example.petwelfare.ui.begin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentLoad3Binding
import com.example.petwelfare.databinding.FragmentLoad4Binding

class LoadFragment3 : Fragment() {

    private lateinit var binding : FragmentLoad3Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoad3Binding.inflate(inflater, container, false)
        return binding.root
    }
}