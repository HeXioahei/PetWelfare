package com.example.petwelfare.ui.main.add

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityAddBinding
import com.example.petwelfare.ui.main.add.articles.AddArticlesActivity
import com.example.petwelfare.ui.main.add.loss.AddLossActivity
import com.example.petwelfare.ui.main.add.stray.AddStrayActivity

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        binding.returnBtn.setOnClickListener {
            finish()
        }

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