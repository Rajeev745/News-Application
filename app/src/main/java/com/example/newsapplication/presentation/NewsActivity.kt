package com.example.newsapplication.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ActivityNewsBinding
import com.example.newsapplication.presentation.fragments.main.HomeFragment
import com.example.newsapplication.presentation.fragments.main.ProfileFragment
import com.example.newsapplication.presentation.fragments.main.SavedFragment
import com.example.newsapplication.presentation.fragments.main.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private var mBinding: ActivityNewsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(mBinding?.root)
        replaceFragment(HomeFragment())
        initializeTabLayout()
    }

    private fun initializeTabLayout() {
        mBinding?.bottomNav?.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> replaceFragment(HomeFragment())
                R.id.search_menu -> replaceFragment(SearchFragment())
                R.id.saved_menu -> replaceFragment(SavedFragment())
                R.id.profile_menu -> replaceFragment(ProfileFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}