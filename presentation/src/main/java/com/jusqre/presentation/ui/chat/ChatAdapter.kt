package com.jusqre.presentation.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jusqre.domain.model.Chat
import com.jusqre.presentation.databinding.ItemChatBinding

class ChatAdapter : ListAdapter<Chat, ChatAdapter.ViewHolder>(diffUtil) {
    class ViewHolder(
        private val binding: ItemChatBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Chat) {
            binding.cvMyChat.isVisible = item.isMyText
            binding.cvBotChat.isVisible = item.isMyText.not()
            when (item.isMyText) {
                true -> {
                    binding.tvMyChat.text = item.text
                }
                false -> {
                    binding.tvBotChat.text = item.text
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Chat>() {
            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.text == newItem.text && oldItem.isMyText == newItem.isMyText
            }
        }
    }
}