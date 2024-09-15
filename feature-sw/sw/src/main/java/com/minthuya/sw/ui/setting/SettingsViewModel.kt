package com.minthuya.sw.ui.setting

import androidx.lifecycle.viewModelScope
import com.minthuya.component.viewmodel.BaseViewModel
import com.minthuya.networkkit.UiResult
import com.minthuya.networkkit.execute
import com.minthuya.networkkit.postEmit
import com.minthuya.sw.domain.usecase.GetSWLanguagesUseCase
import com.minthuya.sw.domain.usecase.SettingsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getSWLanguagesUseCase: GetSWLanguagesUseCase,
    private val settingsUseCase: SettingsUseCase
) : BaseViewModel() {

    private val _languagesState = MutableStateFlow<UiResult<List<String>>>(UiResult.Loading())
    val languagesState = _languagesState.asStateFlow()

    private val _setSettingState = MutableStateFlow<UiResult<Boolean>>(UiResult.Loading())
    val setSettingState = _setSettingState.asStateFlow()

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

    fun setSettings(settings: Map<String, String>) = viewModelScope.launch {
        settingsUseCase.setSettings(settings).execute(
            success = {
                _setSettingState.postEmit(this, UiResult.Success(it))
            },
            error = {
                _setSettingState.postEmit(this, UiResult.Error(it))
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