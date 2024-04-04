package com.example.petwelfare.ui.mine.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityMyArticlesBinding

class MyArticlesActivity : AppCompatActivity() {

    lateinit var binding : ActivityMyArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}