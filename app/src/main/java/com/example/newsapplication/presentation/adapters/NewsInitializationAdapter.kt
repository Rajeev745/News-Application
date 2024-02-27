package com.example.newsapplication.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapplication.databinding.ItemStartingPagesBinding
import com.example.newsapplication.domain.models.starting.StartingInfo

class NewsInitializationAdapter :
    RecyclerView.Adapter<NewsInitializationAdapter.NewsInitializationViewHolder>() {

    private var mContext: Context? = null

    private val differCallback = object : DiffUtil.ItemCallback<StartingInfo>() {
        override fun areItemsTheSame(oldItem: StartingInfo, newItem: StartingInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: StartingInfo, newItem: StartingInfo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsInitializationViewHolder {
        mContext = parent.context
        return NewsInitializationViewHolder(
            ItemStartingPagesBinding.inflate(
                LayoutInflater.from(mContext),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: NewsInitializationViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.bind(item)
    }

    inner class NewsInitializationViewHolder(binding: ItemStartingPagesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val mBinding = binding

        fun bind(item: StartingInfo?) {
            mBinding.apply {
                Glide.with(mContext!!).load(item?.image).into(ivNews)
                tvDescription.text = item?.description
            }
        }
    }
}