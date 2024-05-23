package com.example.petwelfare.ui.item.stray

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityStrayBinding

class StrayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStrayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStrayBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}