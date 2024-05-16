package com.example.petwelfare.ui.articles

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityAddArticlesBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.TimeBuilder

class AddArticlesActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddArticlesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ActivityCollector.addActivity(this)

        binding = ActivityAddArticlesBinding.inflate(layoutInflater)

        val viewModel : AddArticlesViewModel by viewModels()

        setContentView(binding.root)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.publishBtn.setOnClickListener {
            val code = viewModel.writeArticle(
                TimeBuilder.getNowTime(),
                binding.content.text.toString(),
                Repository.Authorization,
                listOf()
            )
            if(code == 200) {
                Toast.makeText(this,"发布成功", Toast.LENGTH_SHORT).show()
                Log.d("publishArticle", "success")
            } else {
                Toast.makeText(this,"发布失败", Toast.LENGTH_SHORT).show()
                Log.d("publishArticle", "failed")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}