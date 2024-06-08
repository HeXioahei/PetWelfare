package com.example.petwelfare.ui.main.otheruser.fragment

import com.example.petwelfare.ui.main.mine.itemlist.pet.ItemPetFragment

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