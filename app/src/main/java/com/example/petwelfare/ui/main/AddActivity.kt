package com.example.petwelfare.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityAddBinding
import com.example.petwelfare.ui.item.articles.AddArticlesActivity
import com.example.petwelfare.ui.item.loss.AddLossActivity
import com.example.petwelfare.ui.item.stray.AddStrayActivity

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        binding.article.setOnClickListener {
            val intent = Intent(this, AddArticlesActivity::class.java)
            startActivity(intent)
        }

        binding.loss.setOnClickListener {
            val intent = Intent(this, AddLossActivity::class.java)
            startActivity(intent)
        }

        binding.stray.setOnClickListener {
            val intent = Intent(this, AddStrayActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}