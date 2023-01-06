package com.project.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.project.domain.model.DailyForecastDomainModel
import com.project.response.DailyForecastDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class DailyForecastMapper @Inject constructor() {
    @RequiresApi(Build.VERSION_CODES.O)
    fun mapToForecastDomain(
        dtoList: List<DailyForecastDto>,
        keyForCity: String
    ): List<DailyForecastDomainModel> {
        return dtoList.map { dto ->
            DailyForecastDomainModel(
                0,
                keyForCity,
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
        val outputFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy 'at' hh:mm a", Locale.ENGLISH)
        val date: LocalDateTime = LocalDateTime.parse(dt, inputFormatter)
        val formattedDate: String = outputFormatter.format(date)
        return formattedDate
    }

}