package com.project.weatherAroundTheWorld.views.viewmodel

import com.project.weatherAroundTheWorld.utils.DataResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.CitiesDomainModel
import com.project.domain.usecase.GetCitiesListUseCase
import com.project.weatherAroundTheWorld.utils.ApiConstants.WEATHER_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CitiesListViewModel @Inject constructor(
    var getCitiesListUseCase: GetCitiesListUseCase
) : ViewModel() {
    lateinit var result: List<CitiesDomainModel>
    private val _state = MutableStateFlow<DataResource<List<CitiesDomainModel>>>(DataResource.loading(null))
    val state: StateFlow<DataResource<List<CitiesDomainModel>>>
        get() = _state

    init {
        handleOperation()
    }

    private fun handleOperation() {
        viewModelScope.launch {
                getCitiesListUseCase(WEATHER_API_KEY)
                    .catch {e-> _state.emit(DataResource.error(e.toString(),null)) }
                    .collect{ _state.emit(DataResource.success(it)) }

        }

    }



}
