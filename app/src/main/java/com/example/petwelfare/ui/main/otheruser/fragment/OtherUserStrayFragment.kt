package com.example.petwelfare.ui.main.otheruser.fragment

import com.example.petwelfare.ui.main.mine.itemlist.mine.fragment.MyStrayFragment


class OtherUserStrayFragment(val userId: Long) : MyStrayFragment(userId) {


//    private lateinit var binding: FragmentOtherUserStrayBinding
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentOtherUserStrayBinding.inflate(inflater, container, false)
//
//        val viewModel : OtherUserDetailViewModel by viewModels()
//
//        var mmyStrayList: MutableList<Stray> = mutableListOf(
//            Stray(),
//            Stray(),
//            Stray()
//        )
//        val otherUserActivity = ActivityCollector.otherUserActivity
//
//
//        // 获取列表
//        viewModel.getMmyStray(userId)
//
//        val mmyArticlesAdapter = StrayAdapter(mmyStrayList)
//        binding.recyclerView.adapter = mmyArticlesAdapter
//        val layoutManager = LinearLayoutManager(otherUserActivity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.recyclerView.layoutManager = layoutManager
//
//        viewModel.mmyStray.observe(otherUserActivity) { result->
//            mmyStrayList = result.data
//            mmyArticlesAdapter.notifyDataSetChanged()
//        }
//
//        return binding.root
//    }
}