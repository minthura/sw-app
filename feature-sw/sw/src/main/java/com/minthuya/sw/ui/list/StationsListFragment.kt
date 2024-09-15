package com.minthuya.sw.ui.list

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView.OnItemClickListener
import androidx.activity.OnBackPressedCallback
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
import com.minthuya.sw.util.SettingConstants
import com.minthuya.sw.util.setNavigationIconVisibility
import com.minthuya.sw.util.setSettingsVisible
import com.minthuya.sw.util.setTopNavBarTitle
import com.minthuya.sw.util.setTopNavBarVisibility
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

    private lateinit var onBackPressedCallback: OnBackPressedCallback

    override fun onStart() {
        super.onStart()
        onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                showExitConfirmationBottomSheet()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback)
    }

    override fun onPause() {
        super.onPause()
        onBackPressedCallback.remove()
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
        setTopNavBarVisibility(true)
        setSettingsVisible(true)
        setNavigationIconVisibility(false)
        setTopNavBarTitle(getString(R.string.sw_app_title))
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
        /*binding.languageTextView.setText(currentLanguage)
        binding.stationTextView.setText(currentStation)
        binding.stationTextView.setSimpleItems(resources.getStringArray(R.array.sw_radio_stations))*/
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
        viewModel.getSettingState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is UiResult.Error -> Unit
                is UiResult.Loading -> Unit
                is UiResult.Success -> it.body?.let { settings ->
                    settings.keys.forEach { key ->
                        when (key) {
                            SettingConstants.KEY_STATION -> {
                                currentStation = settings[key].orEmpty()
                                binding.stationTextView.setText(settings[key])
                            }
                            SettingConstants.KEY_LANGUAGE -> {
                                currentLanguage = settings[key].orEmpty()
                                binding.languageTextView.setText(settings[key])
                            }
                        }
                    }
                    binding.stationTextView.setSimpleItems(resources.getStringArray(R.array.sw_radio_stations))
                    viewModel.getLanguages()
                }
            }
        }
    }

    override fun onViewCreated() {
        if (viewModel.uiState.value !is UiResult.Success) {
            viewModel.getStations(0, currentLanguage, currentStation, isChipChecked)
        }
        viewModel.getSettings()
    }

    private fun hideKeyboard() {
        (
            context?.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as? InputMethodManager
            )?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}