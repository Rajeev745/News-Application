package com.example.newsapplication.presentation.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsapplication.R
import com.example.newsapplication.databinding.FragmentHomeBinding
import com.example.newsapplication.domain.models.home.FragmentModel
import com.example.newsapplication.presentation.adapters.ViewPagerAdapter
import com.example.newsapplication.presentation.fragments.home.MediaPlatformFragment
import com.example.newsapplication.presentation.fragments.home.TrendingFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    private var mBinding: FragmentHomeBinding? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        initializeViews()
        return mBinding?.root
    }

    private fun initializeViews() {
        setUpViewPager()
    }

    private fun setUpViewPager() {
        val list = listOf<FragmentModel>(
            FragmentModel(TrendingFragment(), resources.getString(R.string.trending)),
            FragmentModel(MediaPlatformFragment(), resources.getString(R.string.sources))
        )

        viewPagerAdapter = ViewPagerAdapter(requireActivity(), list)

        mBinding?.apply {
            vpHome.adapter = viewPagerAdapter
            TabLayoutMediator(tabHome, vpHome) { tab: TabLayout.Tab, position: Int ->
                tab.text = list[position].title
            }.attach()
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}