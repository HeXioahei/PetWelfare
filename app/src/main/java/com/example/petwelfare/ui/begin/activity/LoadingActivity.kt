package com.example.petwelfare.ui.begin.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityLoadingBinding
import com.example.petwelfare.ui.adapter.viewpageradapter.CommonFragmentStateAdapter
import com.example.petwelfare.ui.begin.fragment.LoadFragment1
import com.example.petwelfare.ui.begin.fragment.LoadFragment2
import com.example.petwelfare.ui.begin.fragment.LoadFragment3
import com.example.petwelfare.ui.begin.fragment.LoadFragment4

class LoadingActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoadingBinding.inflate(layoutInflater)

        ActivityCollector.addActivity(this)
        setContentView(binding.root)

        val fragmentList = listOf(
            LoadFragment1(),
            LoadFragment2(),
            LoadFragment3(),
            LoadFragment4()
        )

        val fragmentAdapter = CommonFragmentStateAdapter(this, fragmentList)
        binding.viewPager.adapter = fragmentAdapter

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeAll()
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}