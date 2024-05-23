package com.example.petwelfare.ui.adapter.viewpageradapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.petwelfare.ui.main.mine.item.collection.ItemCollectionFragment

class CollectionFragmentStateAdapter(val fragment: ItemCollectionFragment, val list: List<Fragment>): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int  = list.size

    override fun createFragment(position: Int): Fragment = list[position]

}