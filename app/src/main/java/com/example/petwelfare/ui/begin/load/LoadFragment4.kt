package com.example.petwelfare.ui.begin.load

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentLoad4Binding
import com.example.petwelfare.ui.begin.LoginActivity


class LoadFragment4 : Fragment() {

    private lateinit var binding : FragmentLoad4Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoad4Binding.inflate(inflater, container, false)

        binding.btn.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, LoginActivity::class.java)
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            ActivityCollector.removeActivity(ActivityCollector.loadingActivity)
        }

        return binding.root



    }


}