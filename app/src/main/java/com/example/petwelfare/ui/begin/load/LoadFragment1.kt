package com.example.petwelfare.ui.begin.load

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentLoad1Binding
import com.example.petwelfare.databinding.FragmentLoad4Binding

class LoadFragment1 : Fragment() {
    private lateinit var binding : FragmentLoad1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoad1Binding.inflate(inflater, container, false)
        return binding.root
    }
}