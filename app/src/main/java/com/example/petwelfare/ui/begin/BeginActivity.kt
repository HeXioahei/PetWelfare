package com.example.petwelfare.ui.begin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.petwelfare.R
import com.example.petwelfare.Token
import com.example.petwelfare.databinding.ActivityBeginBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.ui.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BeginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBeginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.IO).launch {
            Log.d("aa", "aa")
            MineDao.getAuthorization()
            MineDao.getMailbox()

        }
        if (Repository.Authorization!="") {
            Log.d("aaa", "aaa")
            val intent = Intent(this@BeginActivity, MainActivity::class.java)
            this@BeginActivity.startActivity(intent)
        } else {
            Log.d("aaaaa", "aaaaa")
            val intent = Intent(this@BeginActivity, LoginActivity::class.java)
            this@BeginActivity.startActivity(intent)
        }

    }
}