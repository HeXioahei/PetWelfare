package com.example.petwelfare.ui.main.mine.item.pet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.FragmentItemPetBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.logic.model.Pet
import com.example.petwelfare.ui.adapter.listadapter.PetsAdapter
import com.example.petwelfare.ui.main.mine.pet.PetListActivity
import java.util.ArrayList

open class ItemPetFragment(private val userId : Long) : Fragment() {

    private lateinit var binding : FragmentItemPetBinding
//    private var myPetList : MutableList<Pet> = mutableListOf(Pet(), Pet(), Pet(), Pet(), Pet(), Pet())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemPetBinding.inflate(inflater, container, false)

        val viewModel: ItemPetViewModel by viewModels()

        viewModel.getMyPets(userId)

        val mineActivity = ActivityCollector.mineActivity

        var myPetList : MutableList<Pet> = mutableListOf(Pet(), Pet(), Pet(), Pet(), Pet(), Pet())


        viewModel.myPet.observe(mineActivity) { result->
            myPetList = result.data.pets
            Repository.myPetList = myPetList
        }

        val petAdapter = PetsAdapter(myPetList, mineActivity, 1)
        binding.petRecyclerView.adapter = petAdapter
        val layoutInflater = LinearLayoutManager(mineActivity)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL
        binding.petRecyclerView.layoutManager = layoutInflater

        binding.toPetListBtn.setOnClickListener {
            val intent = Intent(mineActivity, PetListActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

}