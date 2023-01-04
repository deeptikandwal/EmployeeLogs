package com.project.weatherAroundTheWorld.data.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyForecastDto(
    @SerializedName("LocalObservationDateTime")
    val localObservationDateTime: String? = null,
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("WeatherText") val weatherText: String,
    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean,
    @SerializedName("IsDayTime") val isDayTime: Boolean,
)

data class Temperature(
    @SerializedName("Metric") val metric: Metric,
)

data class Metric(
    @SerializedName("Value") val value: Double,
    @SerializedName("Unit") val unit: String
)

