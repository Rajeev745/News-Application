package com.example.newsapplication.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapplication.databinding.ItemFilterBinding
import com.example.newsapplication.domain.models.filter.FilterModel

class FilterAdapter(
    val list: List<FilterModel>,
    val listener: OnFilterListener
) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        return FilterViewHolder(ItemFilterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            val previousSelectedPosition = list.indexOfFirst { it.isSelected }
            if (previousSelectedPosition != position) {
                list[previousSelectedPosition].isSelected = false
                list[position].isSelected = true
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(position)
                listener.onClick(position)
            }
        }
    }

    inner class FilterViewHolder(val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterModel) {
            binding.apply {
                rbFilter.isChecked = item.isSelected == true
                tvFilter.text = item.category
            }
        }
    }
}

interface OnFilterListener {
    fun onClick(position: Int)
}