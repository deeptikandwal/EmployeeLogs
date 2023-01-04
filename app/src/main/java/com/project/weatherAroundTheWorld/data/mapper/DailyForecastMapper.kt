package com.project.weatherAroundTheWorld.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.project.weatherAroundTheWorld.data.response.DailyForecastDto
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DailyForecastMapper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToForecastDomain(dtoList: List<DailyForecastDto>): List<DailyForecastDomainModel> {
        return dtoList.map { dto ->
            DailyForecastDomainModel(
                0,
                dto.weatherText,
                dto.temperature.metric.value.toString().plus(dto.temperature.metric.unit),
                getDate(dto.localObservationDateTime.orEmpty()),
                dto.isDayTime,
                dto.hasPrecipitation,
            )
        }.toList()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDate(dt: String?): String {
        val inputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.ENGLISH)
        val outputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("E MMMM d, yyyy", Locale.ENGLISH)
        val date: LocalDate = LocalDate.parse(dt, inputFormatter)
        val formattedDate: String = outputFormatter.format(date)
        return formattedDate
    }
}