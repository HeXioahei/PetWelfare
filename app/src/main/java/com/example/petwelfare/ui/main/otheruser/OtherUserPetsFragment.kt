package com.example.petwelfare.ui.main.otheruser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentOtherUserArticlesBinding
import com.example.petwelfare.databinding.FragmentOtherUserPetsBinding
import com.example.petwelfare.logic.model.Article
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.adapter.listadapter.PetsAdapter
import com.example.petwelfare.ui.main.mine.item.pet.ItemPetFragment

class OtherUserPetsFragment(private val userId : Long) : ItemPetFragment(userId) {

//    private lateinit var binding: FragmentOtherUserPetsBinding
//
//    val viewModel : OtherUserDetailViewModel by viewModels()
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentOtherUserPetsBinding.inflate(inflater, container, false)
//
//        val otherUserActivity = ActivityCollector.otherUserActivity
//
//        // 获取列表
//        viewModel.getMmyPetsInfo(userId)
//
//        val mmyPetsAdapter = PetsAdapter(viewModel.mmyPetsList, otherUserActivity, 1, userId)
//        binding.recyclerView.adapter = mmyPetsAdapter
//        val layoutManager = LinearLayoutManager(otherUserActivity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.recyclerView.layoutManager = layoutManager
//
//        viewModel.mmyPets.observe(otherUserActivity) { result->
//            viewModel.mmyPetsList.clear()
//            viewModel.mmyPetsList.addAll(result.data.pets)
//            mmyPetsAdapter.notifyDataSetChanged()
//        }
//
//        return binding.root
//    }
}