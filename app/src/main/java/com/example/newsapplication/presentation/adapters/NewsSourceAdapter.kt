package com.example.newsapplication.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.databinding.ItemNewsSourcesBinding
import com.example.newsapplication.domain.models.news.sources.NewsSourcesModel

class NewsSourceAdapter(
    val listener: OnNewsSourceListener
) : RecyclerView.Adapter<NewsSourceAdapter.NewsSourceViewHolder>() {


    private var mContext: Context? = null

    private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<NewsSourcesModel>() {
        override fun areItemsTheSame(
            oldItem: NewsSourcesModel,
            newItem: NewsSourcesModel
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: NewsSourcesModel, newItem: NewsSourcesModel
        ): Boolean {
            return oldItem.id == newItem.id
        }
    }

    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): NewsSourceViewHolder {
        mContext = parent.context
        return NewsSourceViewHolder(
            ItemNewsSourcesBinding.inflate(
                LayoutInflater.from(mContext), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsSourceViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)

        holder.mBinding.root.setOnClickListener {
            listener.onClick(item)
        }
    }

    inner class NewsSourceViewHolder(
        binding: ItemNewsSourcesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        val mBinding = binding

        fun bind(item: NewsSourcesModel?) {
            mBinding.apply {
                tvNewsSources.text = item?.name.toString()
            }
        }
    }
}

interface OnNewsSourceListener {
    fun onClick(item: NewsSourcesModel?)
}