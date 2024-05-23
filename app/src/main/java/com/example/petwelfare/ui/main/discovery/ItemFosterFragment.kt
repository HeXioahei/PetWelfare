package com.example.petwelfare.ui.main.discovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.databinding.FragmentItemFosterBinding


class ItemFosterFragment : Fragment() {

    private lateinit var binding : FragmentItemFosterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemFosterBinding.inflate(inflater, container, false)
        return binding.root
    }
}