package com.minthuya.sw.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.minthuya.component.parent
import com.minthuya.sw.R
import com.minthuya.sw.data.service.SyncStationsService
import com.minthuya.sw.databinding.SwEntryFragmentBinding
import com.minthuya.sw.di.DaggerSWComponent
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class EntryFragment : Fragment() {

    private lateinit var binding: SwEntryFragmentBinding

    @Inject
    lateinit var syncStationsService: SyncStationsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerSWComponent.factory().create(this, requireContext(), requireContext().parent()).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SwEntryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                syncStationsService.read().collectLatest {
                    binding.progressBar.isIndeterminate = false
                    binding.progressBar.progress = it.toInt()
                }
            }
        }
    }
}