package com.jusqre.presentation.ui.chat

import android.os.Bundle
import android.text.method.KeyListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.jusqre.presentation.R
import com.jusqre.presentation.databinding.FragmentChatBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val chatViewModel: ChatViewModel by viewModels()
    private val chatAdapter = ChatAdapter()
    private val args: ChatFragmentArgs by navArgs()
    private lateinit var keyListener: KeyListener
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvChat.adapter = chatAdapter
        binding.rvChat.layoutManager = LinearLayoutManager(context).apply {
            stackFromEnd = true
        }
        binding.rvChat.itemAnimator = null
        keyListener = binding.tietInput.keyListener
        chatViewModel.initializeCurrentChatting(args.chattingItem.chatList)
        initializeOnclick()
        initializeCollector()
    }

    private fun initializeOnclick() {
        with(binding) {
            tilInput.setEndIconOnClickListener {
                chatViewModel.sendMyChat(tietInput.text?.toString() ?: "")
            }
        }
    }

    private fun initializeCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                chatViewModel.chatList.collect {
                    if (it.isEmpty()) return@collect
                    chatAdapter.submitList(it) {
                        binding.rvChat.scrollToPosition(it.size - 1)
                    }
                    if (it.last().isMyText) {
                        disableKeyTouch()
                        chatViewModel.getRequest(it.last().text)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                chatViewModel.chatGPTRequest.collectLatest { request ->
                    chatViewModel.getChatGptCompletionStream(request).onEach {
                        chatViewModel.updateBotText(it)
                    }.launchIn(this).invokeOnCompletion { throwable ->
                        throwable?.let {
                            chatViewModel.updateBotText(getString(R.string.chat_error))
                        }
                        enableKeyTouch()
                        chatViewModel.updateDatabase(args.chattingItem.chatId)
                    }
                }
            }
        }
    }

    private fun disableKeyTouch() {
        binding.tietInput.keyListener = null
        binding.tilInput.hint = getString(R.string.chat_waiting)
    }

    private fun enableKeyTouch() {
        binding.tietInput.setText("")
        binding.tilInput.hint = getString(R.string.chat_input)
        binding.tietInput.keyListener = keyListener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}