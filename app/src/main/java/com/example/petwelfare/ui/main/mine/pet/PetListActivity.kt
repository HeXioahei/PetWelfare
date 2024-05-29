package com.example.petwelfare.ui.main.mine.pet

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petwelfare.ActivityCollector
import com.example.petwelfare.databinding.ActivityPetListBinding
import com.example.petwelfare.logic.Repository
import com.example.petwelfare.ui.adapter.listadapter.PetsAdapter

class PetListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPetListBinding

    val viewModel : PetListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPetListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ActivityCollector.addActivity(this)

        val petAdapter = PetsAdapter(viewModel.myPetList, this, 2)
        binding.petRecyclerView.adapter = petAdapter
        val layoutInflater = LinearLayoutManager(this)
        layoutInflater.orientation = LinearLayoutManager.VERTICAL
        binding.petRecyclerView.layoutManager = layoutInflater

        binding.returnBtn.setOnClickListener {
            finish()
        }

        // 添加宠物
        binding.addPetBtn.setOnClickListener {
            val intent = Intent(this, AddPetActivity::class.java)
            startActivity(intent)
        }

        // 手动刷新
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getMyPetList()
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // 更新宠物列表
        viewModel.myPetListLiveData.observe(this) { result->
            viewModel.myPetList.clear()
            viewModel.myPetList.addAll(result.data.pets)
            Repository.myPetList = viewModel.myPetList
            binding.swipeRefresh.isRefreshing = false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
    }
}