package com.example.petwelfare.ui.loss

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
import com.example.petwelfare.databinding.ActivityAddLossBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.TimeBuilder

class AddLossActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddLossBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ActivityCollector.addActivity(this)

        binding = ActivityAddLossBinding.inflate(layoutInflater)

        val viewModel : AddLossViewModel by viewModels()

        setContentView(binding.root)

        binding.returnBtn.setOnClickListener {
            finish()
        }

        val sendTime = TimeBuilder.getNowTime()

        binding.publishBtn.setOnClickListener {
            val code = viewModel.sendLoss(
                binding.name.text.toString(),
                binding.sex.text.toString(),
                binding.type.text.toString(),
                binding.address.text.toString(),
                binding.contact.text.toString(),
                binding.lossTime.text.toString(),
                sendTime,
                binding.description.text.toString(),
                Repository.Authorization,
                listOf()
            )
            if (code == 200) {
                Toast.makeText(this,"发表成功",Toast.LENGTH_SHORT).show()
                Log.d("publishLoss", "success")
            } else {
                Toast.makeText(this,"发表失败",Toast.LENGTH_SHORT).show()
                Log.d("publishLoss", "failed")

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}