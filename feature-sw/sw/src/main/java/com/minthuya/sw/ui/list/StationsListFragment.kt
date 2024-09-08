package com.minthuya.sw.ui.list

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.minthuya.component.base.BaseFragment
import com.minthuya.component.collectWhenStarted
import com.minthuya.component.parent
import com.minthuya.networkkit.UiResult
import com.minthuya.sw.R
import com.minthuya.sw.data.model.RadioStation
import com.minthuya.sw.databinding.SwStationsListFragmentBinding
import com.minthuya.sw.di.DaggerSWComponent
import com.minthuya.sw.ui.StationsAdapter
import com.minthuya.sw.ui.bottomsheet.ExitConfirmationBottomSheet
import javax.inject.Inject

class StationsListFragment : BaseFragment<SwStationsListFragmentBinding>(
    SwStationsListFragmentBinding::inflate
) {

    private var currentLanguage = "Burmese"
    private var currentStation = "Any Station"
    private var isChipChecked = false
    private val languages = mutableListOf<String>()
    private val stations = mutableListOf<RadioStation>()

    @Inject
    lateinit var viewModel: StationsListViewModel

    private val adapter = StationsAdapter {
        viewModel.getStations(it, currentLanguage, currentStation, isChipChecked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback {
            showExitConfirmationBottomSheet()
        }
    }

    private fun showExitConfirmationBottomSheet() {
        ExitConfirmationBottomSheet().apply {
            onPositiveAction = {
                dismiss()
                requireActivity().finish()
            }
            onNegativeAction = {
                dismiss()
            }
        }.show(
            parentFragmentManager,
            ExitConfirmationBottomSheet::class.simpleName
        )
    }

    override fun setupDi() {
        DaggerSWComponent.factory().create(
            this,
            requireContext(),
            findNavController(),
            requireContext().parent()
        ).inject(this)
    }

    override fun setupView() {
        binding.stationsRecyclerView.adapter = adapter
        binding.languageTextView.onItemClickListener =
            OnItemClickListener { p0, p1, index, p3 ->
                currentLanguage = p0.getItemAtPosition(index).toString()
                adapter.clearStations()
                viewModel.getStations(0, currentLanguage, currentStation, isChipChecked)
                hideKeyboard()
            }
        binding.stationTextView.onItemClickListener =
            OnItemClickListener { p0, p1, index, p3 ->
                currentStation = p0.getItemAtPosition(index).toString()
                adapter.clearStations()
                viewModel.getStations(0, currentLanguage, currentStation, isChipChecked)
                hideKeyboard()
            }
        binding.languageTextView.setText(currentLanguage)
        binding.stationTextView.setText(currentStation)
        binding.stationTextView.setSimpleItems(resources.getStringArray(R.array.sw_radio_stations))
        binding.chipToggleLive.setOnCheckedChangeListener { chip, isChecked ->
            isChipChecked = isChecked
            adapter.clearStations()
            viewModel.getStations(0, currentLanguage, currentStation, isChipChecked)
            hideKeyboard()
        }
    }

    override fun setupObservers() {
        viewModel.uiState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is UiResult.Error -> Unit
                is UiResult.Loading -> Unit
                is UiResult.Success -> it.body?.let { it1 ->
                    stations.clear()
                    stations.addAll(it1)
                    adapter.addStations(stations)
                }
            }
        }
        viewModel.languagesState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is UiResult.Error -> Unit
                is UiResult.Loading -> Unit
                is UiResult.Success -> it.body?.let { it1 ->
                    binding.languageTextView.setSimpleItems(it1.toTypedArray())
                    languages.clear()
                    languages.addAll(it1)
                }
            }
        }
    }

    override fun onViewCreated() {
        viewModel.getStations(0, currentLanguage, currentStation, isChipChecked)
        viewModel.getLanguages()
    }

    private fun hideKeyboard() {
        (
            context?.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as? InputMethodManager
            )?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}