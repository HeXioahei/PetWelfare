package com.example.petwelfare.ui.main.mine.itemlist.pet

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.PetWelfareApplication
import com.example.petwelfare.databinding.FragmentItemPetBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.adapter.listadapter.PetsAdapter
import com.example.petwelfare.ui.main.mine.pet.activity.PetListActivity

open class ItemPetFragment(private val userId : Long) : Fragment() {

    private lateinit var binding : FragmentItemPetBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemPetBinding.inflate(inflater, container, false)

        val viewModel: ItemPetViewModel by viewModels()

        if (userId != Repository.myId) {
            binding.toPetListBtn.visibility = View.GONE
        }

        val petAdapter = PetsAdapter(viewModel.myPetList, activity as AppCompatActivity, 1, userId)
        binding.petRecyclerView.adapter = petAdapter
        val layoutInflater = LinearLayoutManager(PetWelfareApplication.context)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL
        binding.petRecyclerView.layoutManager = layoutInflater

        viewModel.getMyPets(userId)
        binding.swipeRefresh.isRefreshing = true

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMyPets(Repository.myId)
        }

        viewModel.myPet.observe(viewLifecycleOwner) { result->
            if (result.data.pets.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            viewModel.myPetList.clear()
            viewModel.myPetList.addAll(result.data.pets)
            petAdapter.notifyDataSetChanged()
            Repository.myPetList = viewModel.myPetList
            binding.swipeRefresh.isRefreshing = false
        }

        binding.toPetListBtn.setOnClickListener {
            val intent = Intent(PetWelfareApplication.context, PetListActivity::class.java)
            // 检查context是否是Activity的Context，如果不是，则添加FLAG_ACTIVITY_NEW_TASK标志
            if (PetWelfareApplication.context !is Activity) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
        }

        return binding.root
    }

}