package com.example.petwelfare.ui.main.mine.item

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.databinding.FragmentItemPetBinding
import com.example.petwelfare.ui.adapter.listadapter.PetsAdapter
import com.example.petwelfare.ui.main.mine.item.mine.ItemMineViewModel
import com.example.petwelfare.ui.main.mine.pet.AddPetActivity

class ItemPetFragment : Fragment() {

    private lateinit var binding : FragmentItemPetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemPetBinding.inflate(inflater, container, false)

        val activity = activity as com.example.petwelfare.ui.main.mine.MineActivity

        val viewModel: ItemPetViewModel by viewModels()
        val viewModel2: ItemMineViewModel by viewModels()

        viewModel.petListData.observe(activity) { result->
            val list = result.getOrNull()
            if (list != null) {
                viewModel.petList = list
            }
        }

        val layoutInflater = LinearLayoutManager(activity)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL

        val petAdapter = PetsAdapter(viewModel.petList.pets, activity)
        binding.petRecyclerView.adapter = petAdapter
        binding.petRecyclerView.layoutManager = layoutInflater

        binding.addPet.setOnClickListener {
            val intent = Intent(activity, AddPetActivity::class.java)

        }

        return binding.root
    }

}