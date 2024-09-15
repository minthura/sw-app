package com.minthuya.sw.ui.sync

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.minthuya.component.base.BaseFragment
import com.minthuya.component.parent
import com.minthuya.sw.data.model.SyncResult
import com.minthuya.sw.data.service.SyncStationsService
import com.minthuya.sw.databinding.SwEntryFragmentBinding
import com.minthuya.sw.di.DaggerSWComponent
import com.minthuya.sw.navigator.SWInternalNavigator
import com.minthuya.sw.util.setTopNavBarVisibility
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

    override fun onStop() {
        super.onStop()
        setTopNavBarVisibility(true)
    }

    override fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collectLatest {
                    when (it) {
                        is SyncResult.DownloadingZip -> {
                            binding.status.text = "Downloading the latest database."
                            binding.progressBar.isIndeterminate = false
                            binding.progressBar.progress = it.progress.toInt()
                        }
                        is SyncResult.Error -> showSnackBarWithAction("Cannot sync the stations.") {
                            viewModel.syncStations()
                        }
                        SyncResult.Indeterminant -> {
                            binding.status.text = "Preparing to update to latest database"
                            binding.progressBar.isIndeterminate = true
                        }
                        is SyncResult.LoadingChannels -> {
                            binding.status.text = "Updating database with the latest records"
                            binding.progressBar.isIndeterminate = false
                            binding.progressBar.progress = it.progress.toInt()
                        }
                        SyncResult.ReadingNxa24File -> {
                            binding.status.text = "Reading the database file. It may take a while."
                            binding.progressBar.isIndeterminate = true
                        }
                        SyncResult.Success -> internalNavigator.navigateToListScreen()
                        is SyncResult.Unzipping -> {
                            binding.status.text = "Extracting the downloaded database."
                            binding.progressBar.isIndeterminate = false
                            binding.progressBar.progress = it.progress.toInt()
                        }

                        is SyncResult.DownloadSuccess -> Unit
                    }
                }
            }
        }
    }

    override fun setupView() {
        setTopNavBarVisibility(false)
    }

    override fun onViewCreated() {
        viewModel.syncStations()
    }
}