package com.example.newsapplication.presentation.fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.newsapplication.common.Resource
import com.example.newsapplication.databinding.FragmentMediaPlatformBinding
import com.example.newsapplication.domain.models.filter.FilterModel
import com.example.newsapplication.domain.models.filter.getCategoryList
import com.example.newsapplication.domain.models.news.sources.NewsSourcesModel
import com.example.newsapplication.presentation.adapters.NewsSourceAdapter
import com.example.newsapplication.presentation.adapters.OnNewsSourceListener
import com.example.newsapplication.presentation.bottomsheet.NewsSourceFilterBottomSheet
import com.example.newsapplication.presentation.bottomsheet.OnNewsSourceBottomSheetListener
import com.example.newsapplication.presentation.viewmodel.TrendingNewsViewModel
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MediaPlatformFragment : Fragment(), OnNewsSourceListener, OnNewsSourceBottomSheetListener {

    private var mBinding: FragmentMediaPlatformBinding? = null

    private val trendingNewsViewModel: TrendingNewsViewModel by viewModels()

    private var filterList: List<FilterModel> = getCategoryList()
    private var currentPosition = 0

    private val newsSourceAdapter by lazy {
        NewsSourceAdapter(this@MediaPlatformFragment)
    }

    @Inject
    lateinit var mNewsSourceFilterBottomSheet: NewsSourceFilterBottomSheet

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMediaPlatformBinding.inflate(layoutInflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeViews()
        fetchNewsSourceData()
        subscribeNewsSourceData()
    }

    private fun initializeViews() {
        mBinding?.apply {
            tvFilter.setOnClickListener {
                openFilterBottomSheet()
            }
        }
        setUpRecyclerView()
    }

    private fun fetchNewsSourceData() {
        val category = filterList[currentPosition].filterCategory
        trendingNewsViewModel.fetchNewsSource(category)
    }

    private fun setUpRecyclerView() {
        mBinding?.rvNewsSources?.apply {
            adapter = newsSourceAdapter
            layoutManager = FlexboxLayoutManager(requireActivity())
        }
    }

    private fun subscribeNewsSourceData() {
        lifecycleScope.launch {
            trendingNewsViewModel.newsSources.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        mBinding?.apply {
                            shimmerViewContainer.stopShimmer()
                            shimmerViewContainer.visibility = GONE
                            rvNewsSources.visibility = VISIBLE
                            newsSourceAdapter.differ.submitList(resource.data?.sources)
                            tvFilter.visibility = VISIBLE
                        }
                    }

                    is Resource.Loading -> {
                        mBinding?.apply {
                            shimmerViewContainer.startShimmer()
                            shimmerViewContainer.visibility = VISIBLE
                            rvNewsSources.visibility = GONE
                            tvFilter.visibility = GONE
                        }
                    }

                    is Resource.Error -> {
                        mBinding?.apply {
                            shimmerViewContainer.stopShimmer()
                            shimmerViewContainer.visibility = GONE
                            textNoNewsFound.visibility = VISIBLE
                            rvNewsSources.visibility = GONE
                            tvFilter.visibility = GONE
                        }
                    }

                    else -> Unit
                }
            }
        }
    }

    private fun openFilterBottomSheet() {
        filterList.forEachIndexed { index, filter ->
            filter.isSelected = index == currentPosition
        }
        if (!mNewsSourceFilterBottomSheet.isAdded) {
            mNewsSourceFilterBottomSheet.setData(this@MediaPlatformFragment, filterList)
            mNewsSourceFilterBottomSheet.show(
                childFragmentManager, mNewsSourceFilterBottomSheet.tag
            )
        }
    }

    override fun onClick(item: NewsSourcesModel?) {
        // Create an intent with the ACTION_VIEW action and the URL as the data
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item?.url ?: ""))
        // Verify that there is an app available to handle this intent
        startActivity(intent)
    }

    override fun onFilterClick(position: Int) {
        currentPosition = position
        fetchNewsSourceData()
    }

    override fun dismiss() {
        mNewsSourceFilterBottomSheet.dismiss()
    }
}