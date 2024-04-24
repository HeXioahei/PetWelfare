package com.example.petwelfare.ui.mine.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityPetBinding

class PetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}