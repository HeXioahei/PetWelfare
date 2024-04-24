package com.example.petwelfare.ui.mine.pet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityEditPetInfoBinding

class EditPetInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditPetInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPetInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}