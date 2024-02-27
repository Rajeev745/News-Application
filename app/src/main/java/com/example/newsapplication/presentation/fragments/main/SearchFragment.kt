package com.example.newsapplication.presentation.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapplication.common.Resource
import com.example.newsapplication.databinding.FragmentSearchBinding
import com.example.newsapplication.presentation.viewmodel.NewsSearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var mBinding: FragmentSearchBinding? = null
    private val newsSearchViewModel: NewsSearchViewModel by viewModels()

    companion object {
        const val TAG = "SearchFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSearchBinding.inflate(layoutInflater)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsSearchViewModel.getNewsSearch("ben stokes", "", 1)
        observeSearchedResponse()
    }

    private fun observeSearchedResponse() {
        lifecycleScope.launch {
            newsSearchViewModel.newsSearchResponse.collect() {
                when (it) {
                    is Resource.Success -> {
                        Log.d(TAG, "${it.data}")
                    }

                    is Resource.Loading -> {
                        Log.d(TAG, "Loading")
                    }

                    is Resource.Error -> {
                        Log.d(TAG, "${it.message}")
                    }

                    else -> Unit
                }
            }
        }
    }
}