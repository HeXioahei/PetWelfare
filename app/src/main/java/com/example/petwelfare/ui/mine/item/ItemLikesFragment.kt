package com.example.petwelfare.ui.mine.item

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.R

class ItemLikesFragment : Fragment() {

    companion object {
        fun newInstance() = ItemLikesFragment()
    }

    private lateinit var viewModel: ItemLikesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_item_likes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ItemLikesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}