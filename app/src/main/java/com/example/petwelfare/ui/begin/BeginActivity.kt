package com.example.petwelfare.ui.begin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.petwelfare.databinding.ActivityBeginBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.dao.MineDao
import com.example.petwelfare.ui.begin.load.LoadingActivity
import com.example.petwelfare.ui.main.MainActivity
import com.example.petwelfare.utils.ActivityCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BeginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBeginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)
        ActivityCollector.beginActivity = this


//        CoroutineScope(Dispatchers.IO).launch {
//            Log.d("aa", "aa")
////            MineDao.getAuthorization()
////            MineDao.getMailbox()
//        }
//        if (Repository.Authorization!="") {
//            Log.d("aaa", "aaa")
//            val intent = Intent(this@BeginActivity, MainActivity::class.java)
//            this@BeginActivity.startActivity(intent)
//        } else {
//            Log.d("aaaaa", "aaaaa")
//            val intent = Intent(this@BeginActivity, LoginActivity::class.java)
//            this@BeginActivity.startActivity(intent)
//        }

        lifecycleScope.launch {
            Log.d("Authorization", Repository.Authorization)
            MineDao.getUserId().first { userId->
                Repository.myId = userId
                true
            }
            MineDao.getAuthorization().first { Authorization->
                Repository.Authorization = Authorization
                Log.d("Authorization", Repository.Authorization)
                true
            }
            Log.d("refreshToken", Repository.refreshToken)
            MineDao.getRefreshToken().first { refreshToken->
                Repository.refreshToken = refreshToken
                true
            }
            MineDao.getMailbox().first { mailbox->
                Repository.mailbox = mailbox
                true
            }
            if (Repository.Authorization!="") {
                Log.d("aaa", "aaa")
                val intent = Intent(this@BeginActivity, MainActivity::class.java)
                this@BeginActivity.startActivity(intent)
            } else {
                Log.d("aaaaa", "aaaaa")
                val intent = Intent(this@BeginActivity, LoadingActivity::class.java)
                this@BeginActivity.startActivity(intent)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onResume() {
        super.onResume()

    }
}