package com.example.petwelfare.ui.begin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.petwelfare.databinding.FragmentLoginBinding
import com.example.petwelfare.logic.Repository

class LoginFragment(private val activity: LoginActivity) : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.loginBtn.setOnClickListener {
            val mailbox = binding.loginMailbox.text.toString()
            val psd = binding.loginPassword.text.toString()
            val code = viewModel.login(mailbox, psd)
            if (code == 200) {
                Repository.mailbox = mailbox
                activity.toMainActivity()
            }
        }
        binding.toRestorePsd.setOnClickListener {
            val intent = Intent(activity, RestorePsdActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }
}