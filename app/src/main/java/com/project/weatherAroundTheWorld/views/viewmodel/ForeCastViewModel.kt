package com.project.weatherAroundTheWorld.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.domain.model.DailyForecastDomainModel
import com.project.domain.usecase.GetForecastUseCase
import com.project.weatherAroundTheWorld.utils.ApiConstants
import com.project.weatherAroundTheWorld.utils.DataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ForeCastViewModel @Inject constructor(var forecastUseCase: GetForecastUseCase) :
    ViewModel() {
    private var _state = MutableStateFlow<DataResource<List<DailyForecastDomainModel>>>(DataResource.loading(null))
    val state: StateFlow<DataResource<List<DailyForecastDomainModel>>>
        get() = _state


    fun getForeCastList(keyForCity:String) {
        viewModelScope.launch {
              forecastUseCase(keyForCity,ApiConstants.WEATHER_API_KEY)
                  .catch {e-> _state.emit(DataResource.error(e.toString(),null))}
                  .collect{_state.emit(DataResource.success(it)) }

        }

    }


}