package com.minthuya.sw.ui.list

import androidx.lifecycle.viewModelScope
import com.minthuya.component.viewmodel.BaseViewModel
import com.minthuya.networkkit.UiResult
import com.minthuya.networkkit.execute
import com.minthuya.networkkit.postEmit
import com.minthuya.sw.data.model.RadioStation
import com.minthuya.sw.domain.usecase.GetSWLanguagesUseCase
import com.minthuya.sw.domain.usecase.GetSWStationsUseCase
import com.minthuya.sw.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StationsListViewModel(
    private val getSWStationsUseCase: GetSWStationsUseCase,
    private val getSWLanguagesUseCase: GetSWLanguagesUseCase,
    private val settingsUseCase: SettingsUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiResult<List<RadioStation>>>(UiResult.Loading())
    val uiState = _uiState.asStateFlow()

    private val _languagesState = MutableStateFlow<UiResult<List<String>>>(UiResult.Loading())
    val languagesState = _languagesState.asStateFlow()

    private val _getSettingState = MutableStateFlow<UiResult<Map<String, String>>>(UiResult.Loading())
    val getSettingState = _getSettingState.asStateFlow()

    fun getLanguages() = viewModelScope.launch {
        _languagesState.emit(UiResult.Loading())
        getSWLanguagesUseCase().execute(
            success = {
                _languagesState.postEmit(this, UiResult.Success(it))
            },
            error = {
                _languagesState.postEmit(this, UiResult.Error(it))
            }
        )
    }

    fun getStations(offset: Int, language: String, station: String, isLiveNow: Boolean) = viewModelScope.launch {
        _uiState.emit(UiResult.Loading())
        val st = station.takeIf { it != "Any Station" }
        getSWStationsUseCase(offset = offset, language = language, station = st, isLiveNow = isLiveNow).execute(
            success = {
                _uiState.postEmit(this, UiResult.Success(it))
            },
            error = {
                _uiState.postEmit(this, UiResult.Error(it))
            }
        )
    }

    fun getSettings() = viewModelScope.launch {
        settingsUseCase.getSettings().execute(
            success = {
                _getSettingState.postEmit(this, UiResult.Success(it))
            },
            error = {
                _getSettingState.postEmit(this, UiResult.Error(it))
            }
        )
    }
}