package com.example.newsapplication.presentation.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapplication.databinding.BottomSheetNewsFilterBinding
import com.example.newsapplication.domain.models.filter.FilterModel
import com.example.newsapplication.presentation.adapters.FilterAdapter
import com.example.newsapplication.presentation.adapters.OnFilterListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsSourceFilterBottomSheet @Inject constructor() : BottomSheetDialogFragment(),
    OnFilterListener {

    private var mBinding: BottomSheetNewsFilterBinding? = null
    private var listener: OnNewsSourceBottomSheetListener? = null
    private var filterList: List<FilterModel> = listOf()
    private var currentSelectedPosition = 0

    private val mFilterAdapter by lazy {
        FilterAdapter(filterList, this@NewsSourceFilterBottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = BottomSheetNewsFilterBinding.inflate(inflater, container, false)
        initializeViews()
        return mBinding?.root
    }

    private fun initializeViews() {
        mBinding?.rvFilterCategories?.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL,
                false
            )
            adapter = mFilterAdapter
        }

        mBinding?.ivCloseBtn?.setOnClickListener {
            listener?.dismiss()
        }

        mBinding?.tvApply?.setOnClickListener {
            listener?.onFilterClick(currentSelectedPosition)
            listener?.dismiss()
        }
    }

    fun setData(listener: OnNewsSourceBottomSheetListener, list: List<FilterModel>) {
        this.listener = listener
        this.filterList = list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    override fun onClick(position: Int) {
        currentSelectedPosition = position
    }
}

interface OnNewsSourceBottomSheetListener {
    fun onFilterClick(position: Int)
    fun dismiss()
}