package com.jusqre.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jusqre.domain.model.ChattingItem
import com.jusqre.domain.repository.ChatRepository
import com.jusqre.presentation.model.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val chatRepository: ChatRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState.READY)
    val uiState: StateFlow<UIState> = _uiState
    private val _chattingListState = MutableSharedFlow<List<ChattingItem>>()
    val chattingListState: SharedFlow<List<ChattingItem>> = _chattingListState
    fun getItem() {
        viewModelScope.launch {
            with(chatRepository.getAll()) {
                if (isEmpty()) {
                    _uiState.emit(UIState.EMPTY_LIST)
                } else {
                    _uiState.emit(UIState.GET_LIST)
                    _chattingListState.emit(this)
                }
            }

        }
    }

    fun createNewChat(): ChattingItem {
        val newID = "test${Random.nextInt(100000)}"
        viewModelScope.launch {
            chatRepository.insert(newID, listOf())
        }
        return ChattingItem(newID, listOf(),"")
    }

    fun resetUIState() {
        viewModelScope.launch {
            _uiState.emit(UIState.READY)
        }
    }
}