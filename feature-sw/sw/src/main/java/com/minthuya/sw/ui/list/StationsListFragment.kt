package com.minthuya.sw.ui.list

import android.content.Context
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.minthuya.component.base.BaseFragment
import com.minthuya.component.collectWhenStarted
import com.minthuya.component.parent
import com.minthuya.networkkit.UiResult
import com.minthuya.sw.R
import com.minthuya.sw.databinding.SwStationsListFragmentBinding
import com.minthuya.sw.di.DaggerSWComponent
import com.minthuya.sw.ui.StationsAdapter
import javax.inject.Inject

class StationsListFragment: BaseFragment<SwStationsListFragmentBinding>(
    SwStationsListFragmentBinding::inflate
) {

    private var currentLanguage = "English"
    private val languages = mutableListOf<String>()
    @Inject
    lateinit var viewModel: StationsListViewModel

    private val adapter = StationsAdapter {
        viewModel.getStations(it, currentLanguage)
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
                viewModel.getStations(0, currentLanguage)
                hideKeyboard()
            }
        binding.languageTextView.setText(currentLanguage)
    }

    override fun setupObservers() {
        viewModel.uiState.collectWhenStarted(viewLifecycleOwner) {
            when (it) {
                is UiResult.Error -> Unit
                is UiResult.Loading -> Unit
                is UiResult.Success -> it.body?.let { it1 -> adapter.addStations(it1) }
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
        viewModel.getStations(0, "English")
        viewModel.getLanguages()
    }

    private fun hideKeyboard() {
        (context?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}