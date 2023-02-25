package com.jusqre.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jusqre.domain.model.ChattingItem
import com.jusqre.presentation.databinding.ItemChattingBinding

class ChattingAdapter(
    val onClick: (ChattingItem) -> Unit
) : ListAdapter<ChattingItem, ChattingAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(
        private val binding: ItemChattingBinding,
        private val onClick: (ChattingItem) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChattingItem) {
            binding.tvChattingTitle.text = item.chatId
            binding.tvChattingContent.text = item.lastChat
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemChattingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<ChattingItem>() {
            override fun areItemsTheSame(oldItem: ChattingItem, newItem: ChattingItem): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: ChattingItem, newItem: ChattingItem): Boolean {
                return oldItem.chatId == newItem.chatId && oldItem.lastChat == newItem.lastChat
            }
        }
    }
}