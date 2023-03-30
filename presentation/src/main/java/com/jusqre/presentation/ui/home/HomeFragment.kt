package com.jusqre.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jusqre.presentation.databinding.FragmentHomeBinding
import com.jusqre.presentation.model.UIState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
        binding.rvChatList.itemAnimator = null
        binding.titleVisibility = false
        homeViewModel.getItem()
        initializeCollector()
    }

    private fun initializeCollector() {
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            homeViewModel.uiState.collectLatest {
                binding.titleVisibility = it == UIState.EMPTY_LIST
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            homeViewModel.chattingListState.collectLatest {
                chattingAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}