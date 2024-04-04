package com.example.petwelfare.ui.orgnization

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityOrgBinding

class OrgActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOrgBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrgBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}