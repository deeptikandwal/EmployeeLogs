package com.project.weatherAroundTheWorld.data.response

import com.google.gson.annotations.SerializedName
import java.util.*

data class DailyForecastDto(
    @SerializedName("Headline")
    val headline: Headline? = null,
    @SerializedName("DailyForecasts")
    val dailyForecasts: List<DailyForecasts>? = null,

    )

data class Headline(
    @SerializedName("Text") val text: String,
    @SerializedName("EffectiveDate") val effectiveDate: String,
)

data class DailyForecasts(
    @SerializedName("Temperature") val temperature: Temperature,
    @SerializedName("Day") val day: DayNight,
    @SerializedName("Night") val night: DayNight,
    @SerializedName("Date") val effectiveDate: String,
)

data class Temperature(
    @SerializedName("Minimum") val minvalue: MaxMin,
    @SerializedName("Maximum") val maxvalue: MaxMin,
)

data class DayNight(@SerializedName("IconPhrase") val iconPhrase: String)
data class MaxMin(
    @SerializedName("Value") val value: Int,
    @SerializedName("Unit") val unit: String
)

