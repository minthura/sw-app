package com.minthuya.sample.ui

import androidx.lifecycle.viewModelScope
import com.minthuya.component.viewmodel.BaseViewModel
import com.minthuya.networkkit.UiResult
import com.minthuya.networkkit.execute
import com.minthuya.networkkit.postEmit
import com.minthuya.sample.data.dtos.Weather
import com.minthuya.sample.domain.GetWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SampleViewModel(
    private val getWeatherUseCase: GetWeatherUseCase
) : BaseViewModel() {

    private val _uiState = MutableStateFlow<UiResult<Weather>>(UiResult.Loading())
    val uiState = _uiState.asStateFlow()

    fun fetchWeather() = viewModelScope.launch {
        _uiState.emit(UiResult.Loading())
        getWeatherUseCase.invoke().execute(
            success = {
                _uiState.postEmit(UiResult.Success(it))
            },
            error = {
                _uiState.postEmit(UiResult.Error(it))
            }
        )
    }
}