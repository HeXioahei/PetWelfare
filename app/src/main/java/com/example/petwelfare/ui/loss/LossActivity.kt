package com.example.petwelfare.ui.loss

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityLossBinding

class LossActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLossBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLossBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}