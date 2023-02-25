package com.jusqre.presentation.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusqre.domain.model.Chat
import com.jusqre.domain.repository.ChatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _chatList = MutableStateFlow(emptyList<Chat>())
    val chatList: StateFlow<List<Chat>> = _chatList
    private val _chatGPTRequest = MutableSharedFlow<String>()
    val chatGPTRequest: SharedFlow<String> = _chatGPTRequest

    fun sendMyChat(text: String) {
        viewModelScope.launch {
            val myChat = Chat(text, true)
            _chatList.emit(chatList.value + myChat)
        }
    }

    fun getRequest(text: String) {
        viewModelScope.launch {
            _chatList.emit(chatList.value + Chat("", false))
            _chatGPTRequest.emit("Query: $text")
        }
    }

    fun initializeCurrentChatting(chatList: List<Chat>) {
        viewModelScope.launch {
            _chatList.emit(chatList)
        }
    }

    fun updateDatabase(id: String) {
        viewModelScope.launch {
            chatRepository.update(id, chatList.value)
        }
    }

    fun updateBotText(newText: String) {
        if (newText.isNotBlank() || chatList.value.last().text.isNotBlank()) {
            val currentList = _chatList.value.toMutableList()
            val lastChat = currentList.last()
            val newChat = Chat(lastChat.text + newText, lastChat.isMyText)
            currentList[currentList.size - 1] = newChat
            _chatList.value = currentList.toList()
        }
    }

    fun getChatGptCompletionStream(text: String): Flow<String> =
        chatRepository.getChatFlowByKeyword(text).flowOn(Dispatchers.IO)
}