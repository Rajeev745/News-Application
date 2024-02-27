package com.example.newsapplication.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsapplication.domain.models.home.FragmentModel

class ViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val list: List<FragmentModel>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position].fragment
    }
}