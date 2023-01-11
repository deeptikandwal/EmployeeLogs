package com.project.domain.model
data class DailyForecastDomainModel(
    val id:Int,
    val keyForCity:String,
    val text:String,
    val currentTemperature: String,
    val date:String,
    val isDayTime:Boolean,
    val hasprecipitation:Boolean
)