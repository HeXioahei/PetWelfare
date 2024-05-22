package com.example.petwelfare.ui.mine.item.mine

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentMyStrayBinding


class MyStrayFragment : Fragment() {

    private lateinit var binding : FragmentMyStrayBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyStrayBinding.inflate(inflater, container,false)
        return binding.root
    }
}