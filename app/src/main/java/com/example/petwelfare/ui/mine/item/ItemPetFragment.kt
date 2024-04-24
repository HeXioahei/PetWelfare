package com.example.petwelfare.ui.mine.item

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.R
import com.example.petwelfare.databinding.FragmentItemPetBinding
import com.example.petwelfare.ui.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.listadapter.PetsAdapter
import com.example.petwelfare.ui.mine.MineActivity

class ItemPetFragment : Fragment() {

    private lateinit var binding : FragmentItemPetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentItemPetBinding.inflate(inflater, container, false)

        val activity = activity as MineActivity

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

        return binding.root
    }

}