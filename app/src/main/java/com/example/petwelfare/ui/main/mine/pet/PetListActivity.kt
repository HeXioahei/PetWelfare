package com.example.petwelfare.ui.main.mine.pet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.R
import com.example.petwelfare.databinding.ActivityPetListBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.adapter.listadapter.PetsAdapter

class PetListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPetListBinding
    private var myPetList = Repository.myPetList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        val viewModel : PetListViewModel by viewModels()

        val petAdapter = PetsAdapter(myPetList, this)
        binding.petRecyclerView.adapter = petAdapter
        val layoutInflater = LinearLayoutManager(this)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL
        binding.petRecyclerView.layoutManager = layoutInflater

        binding.returnBtn.setOnClickListener {
            finish()
        }

        binding.addPetBtn.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMyPetList()
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        viewModel.myPetList.observe(this) { result->
            myPetList = result.data.pets
            Repository.myPetList = myPetList
            binding.swipeRefresh.isRefreshing = false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}