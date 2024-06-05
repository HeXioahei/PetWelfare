package com.example.petwelfare.ui.begin.load

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.ActivityLoadingBinding
import com.example.petwelfare.ui.adapter.viewpageradapter.CommonFragmentStateAdapter

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
        ActivityCollector.removeActivity(this)
    }

    override fun onRestart() {
        super.onRestart()
        finish()
    }
}