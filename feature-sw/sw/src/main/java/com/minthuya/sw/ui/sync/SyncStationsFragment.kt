package com.minthuya.sw.ui.sync

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.minthuya.component.base.BaseFragment
import com.minthuya.component.parent
import com.minthuya.networkkit.UiResult
import com.minthuya.sw.data.model.SyncResult
import com.minthuya.sw.data.service.SyncStationsService
import com.minthuya.sw.databinding.SwEntryFragmentBinding
import com.minthuya.sw.di.DaggerSWComponent
import com.minthuya.sw.navigator.SWInternalNavigator
import com.minthuya.sw.ui.StationsAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SyncStationsFragment : BaseFragment<SwEntryFragmentBinding>(
    SwEntryFragmentBinding::inflate
) {

    @Inject
    lateinit var syncStationsService: SyncStationsService

    @Inject
    lateinit var viewModel: SyncStationsViewModel

    @Inject
    lateinit var internalNavigator: SWInternalNavigator

    override fun setupDi() {
        DaggerSWComponent.factory().create(
            this,
            requireContext(),
            findNavController(),
            requireContext().parent()
        ).inject(this)
    }

    override fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        is SyncResult.Error -> showSnackBar("Cannot sync the stations.") {
                            viewModel.syncStations()
                        }
                        is SyncResult.Loading -> {
                            it.progress?.toInt()?.let {
                                binding.progressBar.isIndeterminate = false
                                binding.progressBar.progress = it
                            } ?: {
                                binding.progressBar.isIndeterminate = true
                            }
                        }
                        is SyncResult.Success -> {
                            internalNavigator.navigateToListScreen()
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated() {
        viewModel.syncStations()
    }
}