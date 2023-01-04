package com.project.weatherAroundTheWorld.views.viewState
import com.project.weatherAroundTheWorld.domain.model.CitiesDomainModel

sealed class WeatherState{
    object IDLE:WeatherState()
    object LOADING:WeatherState()
    data class SUCCESS(val cities: List<CitiesDomainModel>) : WeatherState()
    data class ERROR(val error: String?) : WeatherState()
}
