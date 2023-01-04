package com.project.weatherAroundTheWorld.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.weatherAroundTheWorld.domain.usecase.GetForecastUseCase
import com.project.weatherAroundTheWorld.utils.ApiConstants
import com.project.weatherAroundTheWorld.views.viewState.ForeCastState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForeCastViewModel @Inject constructor(var forecastUseCase: GetForecastUseCase) :
    ViewModel() {
    private var _state = MutableStateFlow<ForeCastState>(ForeCastState.IDLE)
    val state: StateFlow<ForeCastState>
        get() = _state


    @OptIn(FlowPreview::class)
    fun getForeCastList(keyForCity:String) {
        viewModelScope.launch {
            _state.emit(ForeCastState.LOADING)
            try {
                val result = forecastUseCase(keyForCity,ApiConstants.WEATHER_API_KEY).flatMapConcat {
                    list->list.asFlow()
                }.toList()
                _state.emit(ForeCastState.SUCCESS(result))
            } catch (e: Exception) {
                _state.emit(ForeCastState.ERROR(e.toString()))
            }
        }

    }


}