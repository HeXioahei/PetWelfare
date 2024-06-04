package com.example.petwelfare.ui.main.mine

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentMeBinding
import com.example.petwelfare.logic.Repository


class MeFragment : Fragment() {

    private lateinit var binding : FragmentMeBinding
    private val mainActivity = ActivityCollector.mainActivity
    private val viewModel : MineViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMeBinding.inflate(inflater, container, false)

        binding.go.setOnClickListener {
            val intent = Intent(mainActivity, MineActivity::class.java)
            startActivity(intent)
        }

        viewModel.getUserDetail(Repository.myId)

        MineViewModel.userDetailLiveData.observe(viewLifecycleOwner) { result->
            MineViewModel.userDetail = result
            binding.fans.text = MineViewModel.userDetail.fan_nums.toString()
            binding.follows.text = (MineViewModel.userDetail.follow_nums + MineViewModel.userDetail.collect_orgs_nums).toString()
            binding.username.text = MineViewModel.userDetail.username
            binding.personality.text = MineViewModel.userDetail.personality
            binding.headImage.let { Glide.with(mainActivity).load(GlideUrl(MineViewModel.userDetail.head_image, Repository.lazyHeaders)).into(it) }
        }

        return binding.root
    }

}