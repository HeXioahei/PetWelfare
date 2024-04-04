package com.example.petwelfare.ui.begin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityRestorePsdBinding
import com.example.petwelfare.logic.network.PetWelfareNetwork
import kotlinx.coroutines.GlobalScope

class RestorePsdActivity : AppCompatActivity() {

    lateinit var binding : ActivityRestorePsdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestorePsdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: RestorePsdViewModel by viewModels()

        binding.getVerificationBtn.setOnClickListener {
            viewModel.sendMailbox(binding.mailboxInReset.text.toString())
        }

        binding.resetBtn.setOnClickListener {
            viewModel.resetPsd(
                binding.mailboxInReset.text.toString(),
                binding.verificationInReset.text.toString(),
                binding.psdInReset.text.toString(),
                binding.confirmPsdInReset.text.toString()
            )
        }

    }
}