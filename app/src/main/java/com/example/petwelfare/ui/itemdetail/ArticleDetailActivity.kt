package com.example.petwelfare.ui.itemdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityArticleDetailBinding

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityArticleDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)


    }
}