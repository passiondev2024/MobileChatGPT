package com.jusqre.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jusqre.presentation.databinding.FragmentHomeBinding
import com.jusqre.presentation.model.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val chattingAdapter = ChattingAdapter {
        findNavController().navigate(
            HomeFragmentDirections.actionNavigationHomeToNavigationChat(
                it
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnChat.setOnClickListener {
            homeViewModel.resetUIState()
            findNavController().navigate(
                HomeFragmentDirections.actionNavigationHomeToNavigationChat(
                    homeViewModel.createNewChat()
                )
            )
        }
        binding.rvChatList.adapter = chattingAdapter
        binding.rvChatList.layoutManager = LinearLayoutManager(context)
        binding.tvEmpty.isVisible = false
        homeViewModel.getItem()
        initializeCollector()
    }

    private fun initializeCollector() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeViewModel.uiState.collectLatest {
                    if (it == UIState.EMPTY_LIST) {
                        binding.tvEmpty.isVisible = true
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                homeViewModel.chattingListState.collectLatest {
                    chattingAdapter.submitList(it)
                }
            }
        }
    }

}