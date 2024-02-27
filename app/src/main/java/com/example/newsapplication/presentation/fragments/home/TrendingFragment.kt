package com.example.newsapplication.presentation.fragments.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.databinding.FragmentTrendingBinding
import com.example.newsapplication.presentation.adapters.OnTrendingNewsClickListener
import com.example.newsapplication.presentation.adapters.TrendingNewsAdapter
import com.example.newsapplication.presentation.viewmodel.TrendingNewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TrendingFragment : Fragment(), OnTrendingNewsClickListener {

    private var mBinding: FragmentTrendingBinding? = null
    private val trendingNewsViewModel: TrendingNewsViewModel by viewModels()
    private val trendingNewsAdapter by lazy {
        TrendingNewsAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentTrendingBinding.inflate(layoutInflater)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeTrendingNews()
        setUpRecyclerView()
    }


    private fun setUpRecyclerView() {
        mBinding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(), LinearLayoutManager.VERTICAL, false
            )
            adapter = trendingNewsAdapter
        }
    }

    private fun subscribeTrendingNews() {
        viewLifecycleOwner.lifecycleScope.launch {
            trendingNewsViewModel.fetchTrendingNews("us", "").collectLatest { pagingData ->
                trendingNewsAdapter.submitData(pagingData)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            trendingNewsAdapter.loadStateFlow.collect {
                when (it.refresh) {

                    is LoadState.Loading -> {
                        mBinding?.shimmerViewContainer?.visibility = View.VISIBLE
                        mBinding?.shimmerViewContainer?.startShimmer()
                        Log.d("OCHAKO", "Loading")
                    }

                    is LoadState.Error -> {
                        mBinding?.apply {
                            textNoNewsFound.visibility = View.VISIBLE
                            shimmerViewContainer.stopShimmer()
                            shimmerViewContainer.visibility = View.GONE
                        }
                    }

                    is LoadState.NotLoading -> {
                        mBinding?.apply {
                            textNoNewsFound.visibility = View.GONE
                            shimmerViewContainer.stopShimmer()
                            shimmerViewContainer.visibility = View.GONE
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    override fun saveImage() {

    }
}