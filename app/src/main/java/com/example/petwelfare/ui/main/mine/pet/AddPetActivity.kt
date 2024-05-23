package com.example.petwelfare.ui.main.mine.pet

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityAddPetBinding

class AddPetActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddPetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}