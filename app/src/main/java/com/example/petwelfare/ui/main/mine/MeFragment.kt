package com.example.petwelfare.ui.main.mine

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentMeBinding


class MeFragment : Fragment() {

    private lateinit var binding : FragmentMeBinding
    private val mainActivity = ActivityCollector.mainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeBinding.inflate(inflater, container, false)

        binding.myHeadImage.setOnClickListener {
            val intent = Intent(mainActivity, MineActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }


}