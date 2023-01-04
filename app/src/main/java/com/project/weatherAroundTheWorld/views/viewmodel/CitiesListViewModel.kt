package com.project.weatherAroundTheWorld.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.usecase.GetCitiesListUseCase
import com.project.weatherAroundTheWorld.utils.ApiConstants.WEATHER_API_KEY
import com.project.weatherAroundTheWorld.views.viewState.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    var getCitiesListUseCase: GetCitiesListUseCase
) : ViewModel() {
    lateinit var result: List<com.project.domain.model.CitiesDomainModel>
    private val _state = MutableStateFlow<WeatherState>(WeatherState.IDLE)
    val state: StateFlow<WeatherState>
        get() = _state

    init {
        handleOperation()
    }

    private fun handleOperation() {
        viewModelScope.launch {
            _state.emit(WeatherState.LOADING)
            try {
                getCitiesListUseCase(WEATHER_API_KEY).also {
                        flowCitiesList ->
                    val list = getCitiesListFromFlow(flowCitiesList)
                    _state.emit(WeatherState.SUCCESS(list))
                }
            } catch (exception: Exception) {
                _state.emit(WeatherState.ERROR(exception.toString()))
            }
        }

    }

    private suspend fun getCitiesListFromFlow(flowCityList: Flow<List<com.project.domain.model.CitiesDomainModel>>)= flowCityList.flatMapConcat { listCityDomain -> listCityDomain.asFlow() }.toList()


}
