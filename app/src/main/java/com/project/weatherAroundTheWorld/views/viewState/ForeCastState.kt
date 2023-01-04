package com.project.weatherAroundTheWorld.views.viewState

import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel

sealed class ForeCastState{
    object IDLE:ForeCastState()
    object LOADING:ForeCastState()
    data class SUCCESS(val forcast: List<DailyForecastDomainModel>) : ForeCastState()
    data class ERROR(val error: String?) : ForeCastState()
}
