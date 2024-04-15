package com.example.petwelfare.ui.mine.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.petwelfare.R

class ItemMineFragment : Fragment() {

    companion object {
        fun newInstance() = ItemMineFragment()
    }

    private lateinit var viewModel: ItemMineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_item_mine, container, false)
    }

}