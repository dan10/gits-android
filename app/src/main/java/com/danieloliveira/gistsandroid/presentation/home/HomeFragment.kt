package com.danieloliveira.gistsandroid.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.danieloliveira.gistsandroid.databinding.FragmentHomeBinding
import com.danieloliveira.gistsandroid.domain.base.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GistsAdapter

    private val isFavorites by lazy {
        arguments?.getBoolean("isFavorite", false) ?: false
    }

    @Inject
    internal lateinit var vmFactory: HomeViewModel.AssistedFactory
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModel.provideFactory(vmFactory, isFavorites)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GistsAdapter { item, position ->
            viewModel.saveGist(item, position)
        }
        binding.root.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.flow.collectLatest(adapter::submitData)
        }

        viewModel.saveState.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> adapter.notifyItemChanged(
                    result.data.first,
                    result.data.second
                )
                is Result.Error -> handleErrors(result.exception)
            }
        })
    }

    private fun handleErrors(throwable: Throwable) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}