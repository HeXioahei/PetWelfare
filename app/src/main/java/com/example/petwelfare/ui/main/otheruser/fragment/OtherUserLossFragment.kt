package com.example.petwelfare.ui.main.otheruser.fragment

import androidx.fragment.app.Fragment


class OtherUserLossFragment(val userId: Long) : Fragment() {

//    private lateinit var binding: FragmentOtherUserLossBinding
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentOtherUserLossBinding.inflate(inflater, container, false)
//
//        val viewModel : OtherUserDetailViewModel by viewModels()
//
//        var mmyLossList: MutableList<Loss> = mutableListOf(
//            Loss(),
//            Loss(),
//            Loss()
//        )
//        val otherUserActivity = ActivityCollector.otherUserActivity
//
//        // 获取列表
//        viewModel.getMmyLoss(userId)
//
//        val mmyLossAdapter = LossAdapter(mmyLossList)
//        binding.recyclerView.adapter = mmyLossAdapter
//        val layoutManager = LinearLayoutManager(otherUserActivity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.recyclerView.layoutManager = layoutManager
//
//        viewModel.mmyLoss.observe(otherUserActivity) { result->
//            mmyLossList = result.data
//            mmyLossAdapter.notifyDataSetChanged()
//        }
//
//        return binding.root
//    }


}