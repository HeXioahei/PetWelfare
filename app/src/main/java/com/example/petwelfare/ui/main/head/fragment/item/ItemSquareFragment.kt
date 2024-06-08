package com.example.petwelfare.ui.main.head.fragment.item

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.petwelfare.utils.ActivityCollector
import com.example.petwelfare.databinding.FragmentItemSquareBinding
import com.example.petwelfare.ui.adapter.listadapter.ArticlesAdapter
import com.example.petwelfare.ui.main.head.viewmodel.ItemSquareViewModel

open class ItemSquareFragment : Fragment() {

    private val mainActivity = ActivityCollector.mainActivity

    private lateinit var binding: FragmentItemSquareBinding

    private val viewModel: ItemSquareViewModel by viewModels()

    @SuppressLint("NotifyDataSetChanged", "ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentItemSquareBinding.inflate(inflater, container, false)

        val articlesAdapter = ArticlesAdapter(viewModel.articlesList, "other")
        binding.articlesList.adapter = articlesAdapter
        val layoutManager = LinearLayoutManager(mainActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.articlesList.layoutManager = layoutManager

        // 一开始先获取列表数据
        viewModel.getArticles(1)
        binding.swipeRefresh.isRefreshing = true

        // 启动刷新，进行网络请求获取列表数据
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.order = 1
            viewModel.getArticles(viewModel.order)
            viewModel.delayAction {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        // 响应首次获取以及下拉刷新返回的数据，进行页面显示的更新
        viewModel.articlesResponse.observe(this.viewLifecycleOwner) { result->
            Log.d("articlesResponse", "articlesResponse")
            Log.d("articlesListLength", result.data.size.toString())
            if (result.data.isNotEmpty()) binding.image.visibility = View.INVISIBLE
            else binding.image.visibility = View.VISIBLE
            binding.swipeRefresh.isRefreshing = false
            if (viewModel.order == 1) {
                viewModel.articlesList.clear()
                viewModel.articlesList.addAll(result.data)
                Log.d("articlesList", viewModel.articlesList.toString())
                articlesAdapter.notifyDataSetChanged()
            } else {
                viewModel.articlesList.addAll(result.data)
                articlesAdapter.notifyDataSetChanged()
            }
            binding.swipeRefresh.isRefreshing = false
        }

        var flag = true

        binding.articlesList.setOnTouchListener { _, event ->
            if (event != null) {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        Log.d("RecyclerView", "被触摸了")
                        flag = true
                    }
                }
            }
            // 返回 false 表示不消费这个事件，让它继续传递；返回 true 表示消费这个事件，不再传递
            false
        }

        binding.articlesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                Log.d("scroll", "scroll")
                val layoutManager2 = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager2.findLastVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                if (lastVisibleItemPosition >= totalItemCount - 1) {
                    Log.d("dy2", dy.toString())
                    if (flag) {
                        viewModel.getArticles(++viewModel.order)
                        flag = false
                    }
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.order = 1
        viewModel.getArticles(viewModel.order)
    }

}