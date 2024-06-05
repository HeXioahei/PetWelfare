package com.example.petwelfare.ui.begin.load

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentLoad2Binding
import com.example.petwelfare.databinding.FragmentLoad4Binding

class LoadFragment2 : Fragment() {
    private lateinit var binding : FragmentLoad2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoad2Binding.inflate(inflater, container, false)
        return binding.root
    }
}