package com.example.petwelfare.ui.begin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.petwelfare.databinding.FragmentRegisterBinding

class RegisterFragment(private val activity: LoginActivity) : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]

        binding.getVerificationBtn.setOnClickListener {
            viewModel.sendMailbox(binding.registerMailbox.text.toString())
        }
        binding.registerBtn.setOnClickListener {
            val mailbox = binding.registerMailbox.text.toString()
            val psd = binding.registerPsd.text.toString()
            val verification = binding.verification.text.toString()
            val code = viewModel.register(mailbox, psd, verification)
            if (code == 200) {
                binding.registerMailbox.setText("")
                binding.registerPsd.setText("")
                binding.registerPsdConfirm.setText("")
                binding.verification.setText("")
                activity.replaceFragment("login")
            }
        }
        return binding.root
    }


}