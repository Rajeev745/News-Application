package com.example.newsapplication.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.R
import com.example.newsapplication.databinding.ItemNewsHeadlinesBinding
import com.example.newsapplication.domain.models.news.trending.ArticlesModel

class TrendingNewsAdapter(
    val listener: OnTrendingNewsClickListener
) : PagingDataAdapter<ArticlesModel, TrendingNewsAdapter.TrendingNewsViewHolder>(DIFF_CALLBACK) {

    private var mContext: Context? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesModel>() {
            override fun areItemsTheSame(oldItem: ArticlesModel, newItem: ArticlesModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: ArticlesModel, newItem: ArticlesModel
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TrendingNewsViewHolder {
        mContext = parent.context
        return TrendingNewsViewHolder(
            ItemNewsHeadlinesBinding.inflate(
                LayoutInflater.from(mContext), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TrendingNewsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class TrendingNewsViewHolder(
        binding: ItemNewsHeadlinesBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val mBinding = binding

        fun bind(item: ArticlesModel?) {
            mBinding.apply {
                newsHeadline.text = item?.title
                newsTimeline.text = item?.publishedAt
            }

            Glide.with(itemView).load(item?.urlToImage)
                .placeholder(R.drawable.news_placeholder) // Placeholder image while loading
                .error(R.drawable.news_placeholder) // Image to show if loading fails
                .into(mBinding.newsImage)
        }
    }
}

interface OnTrendingNewsClickListener {
    fun saveImage()
}