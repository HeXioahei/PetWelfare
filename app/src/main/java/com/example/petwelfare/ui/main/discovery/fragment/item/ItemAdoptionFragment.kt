package com.example.petwelfare.ui.main.discovery.fragment.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.databinding.FragmentItemAdoptionBinding


class ItemAdoptionFragment : Fragment() {

    private lateinit var binding : FragmentItemAdoptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemAdoptionBinding.inflate(inflater, container, false)
        return binding.root
    }
}