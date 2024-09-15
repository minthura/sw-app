package com.minthuya.sw.ui.setting

import androidx.navigation.fragment.findNavController
import com.minthuya.component.base.BaseFragment
import com.minthuya.component.collectWhenStarted
import com.minthuya.component.parent
import com.minthuya.networkkit.UiResult
import com.minthuya.sw.R
import com.minthuya.sw.databinding.SwSettingsFragmentBinding
import com.minthuya.sw.di.DaggerSWComponent
import com.minthuya.sw.util.SettingConstants
import com.minthuya.sw.util.setNavigationIconVisibility
import com.minthuya.sw.util.setSettingsVisible
import com.minthuya.sw.util.setTopNavBarTitle
import com.minthuya.sw.util.setTopNavBarVisibility
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class SettingsFragment : BaseFragment<SwSettingsFragmentBinding>(
    SwSettingsFragmentBinding::inflate
) {

    @Inject
    lateinit var viewModel: SettingsViewModel

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
        setSettingsVisible(false)
        setNavigationIconVisibility(true)
        setTopNavBarTitle(getString(R.string.sw_app_settings))
    }

    override fun setupEvents() {
        binding.btnSave.setOnClickListener {
            viewModel.setSettings(
                mapOf(
                    SettingConstants.KEY_STATION to binding.stationTextView.text.toString(),
                    SettingConstants.KEY_LANGUAGE to binding.languageTextView.text.toString()
                )
            )
        }
    }

    override fun setupObservers() {
        viewModel.languagesState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is UiResult.Error -> Unit
                is UiResult.Loading -> Unit
                is UiResult.Success -> it.body?.let { it1 ->
                    binding.languageTextView.setSimpleItems(it1.toTypedArray())
                }
            }
        }
        viewModel.setSettingState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is UiResult.Error -> Unit
                is UiResult.Loading -> Unit
                is UiResult.Success -> showSnackBar("Settings saved.")
            }
        }
        viewModel.getSettingState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is UiResult.Error -> Unit
                is UiResult.Loading -> Unit
                is UiResult.Success -> it.body?.let { settings ->
                    settings.keys.forEach { key ->
                        when (key) {
                            SettingConstants.KEY_STATION -> binding.stationTextView.setText(settings[key])
                            SettingConstants.KEY_LANGUAGE -> binding.languageTextView.setText(settings[key])
                            SettingConstants.KEY_LAST_UPDATED ->
                                binding.txtLastUpdated.text =
                                    getString(R.string.sw_database_last_updated, getLocalTime(settings[key]))
                        }
                    }
                    binding.stationTextView.setSimpleItems(resources.getStringArray(R.array.sw_radio_stations))
                    viewModel.getLanguages()
                }
            }
        }
    }

    private fun getLocalTime(long: String?): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm:ss a")
        long?.let {
            try {
                return Instant.ofEpochMilli(long.toLong()).let {
                    LocalDateTime.ofInstant(it, ZoneId.systemDefault()).run {
                        formatter.format(this)
                    }
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
        }
        return formatter.format(Instant.now())
    }

    override fun onViewCreated() {
        viewModel.getSettings()
    }
}