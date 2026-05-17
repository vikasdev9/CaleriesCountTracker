package com.nutrimind.ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nutrimind.ai.domain.repository.AiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val aiRepository: AiRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("Hello! I'm your NutriMind AI assistant. How can I help you with your nutrition today?", false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    fun sendMessage(query: String) {
        val userMessage = ChatMessage(query, true)
        _messages.value += userMessage

        viewModelScope.launch {
            aiRepository.chatWithAssistant(query).collect { response ->
                val aiMessage = ChatMessage(response, false)
                _messages.value += aiMessage
            }
        }
    }
}

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)
