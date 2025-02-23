package com.minthuya.sw.ui.sync

import androidx.lifecycle.viewModelScope
import com.minthuya.component.viewmodel.BaseViewModel
import com.minthuya.networkkit.execute
import com.minthuya.networkkit.postEmit
import com.minthuya.sw.data.model.SyncResult
import com.minthuya.sw.data.service.SyncStationsService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SyncStationsViewModel(
    private val syncStationsService: SyncStationsService
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<SyncResult>(SyncResult.Indeterminant)
    val uiState = _uiState.asStateFlow()

    fun syncStations() = viewModelScope.launch {
        syncStationsService.autoSync().execute(
            success = {
                _uiState.postEmit(this, it)
            },
            error = {
                syncLocalStations()
            }
        )
    }

    private fun syncLocalStations() = viewModelScope.launch {
        syncStationsService.syncLocal().execute(
            success = {
                _uiState.postEmit(this, it)
            },
            error = {
                _uiState.postEmit(this, SyncResult.Error(it.message))
            }
        )
    }
}