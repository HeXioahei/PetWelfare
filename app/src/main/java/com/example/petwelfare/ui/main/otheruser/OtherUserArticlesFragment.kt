package com.example.petwelfare.ui.main.otheruser

import androidx.fragment.app.Fragment


class OtherUserArticlesFragment(private val userId: Long) : Fragment() {

//    private lateinit var binding: FragmentOtherUserArticlesBinding
//
//    @SuppressLint("NotifyDataSetChanged")
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = FragmentOtherUserArticlesBinding.inflate(inflater, container, false)
//
//        val viewModel : OtherUserDetailViewModel by viewModels()
//
//        var mmyArticlesList: MutableList<Article> = mutableListOf(
//            Article(), Article(),
//            Article(),
//            Article()
//        )
//        val otherUserActivity = ActivityCollector.otherUserActivity
//
//
//        // 获取列表
//        viewModel.getMmyArticles(userId)
//
//        val mmyArticlesAdapter = ArticlesAdapter(mmyArticlesList)
//        binding.mmyArticles.adapter = mmyArticlesAdapter
//        val layoutManager = LinearLayoutManager(otherUserActivity)
//        layoutManager.orientation = LinearLayoutManager.VERTICAL
//        binding.mmyArticles.layoutManager = layoutManager
//
//        viewModel.mmyArticles.observe(otherUserActivity) { result->
//            mmyArticlesList = result.data
//            mmyArticlesAdapter.notifyDataSetChanged()
//        }
//
//        return binding.root
//    }

}