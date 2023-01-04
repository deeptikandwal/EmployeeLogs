package com.project.weatherAroundTheWorld.views.viewState
import com.project.domain.model.CitiesDomainModel

sealed class WeatherState{
    object IDLE:WeatherState()
    object LOADING:WeatherState()
    data class SUCCESS(val cities: List<com.project.domain.model.CitiesDomainModel>) : WeatherState()
    data class ERROR(val error: String?) : WeatherState()
}
