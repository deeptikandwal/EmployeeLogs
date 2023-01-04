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
    fun mapToForecastDomain(dto: DailyForecastDto): DailyForecastDomainModel {
        return DailyForecastDomainModel(
            0,
            dto.headline?.text.orEmpty(),
            dto.dailyForecasts?.get(0)?.day?.iconPhrase.orEmpty(),
            dto.dailyForecasts?.get(0)?.night?.iconPhrase.orEmpty(),
            dto.dailyForecasts?.get(0)?.temperature?.minvalue?.value.toString()
                .plus(dto.dailyForecasts?.get(0)?.temperature?.minvalue?.unit),
            dto.dailyForecasts?.get(0)?.temperature?.maxvalue?.value.toString()
                .plus(dto.dailyForecasts?.get(0)?.temperature?.maxvalue?.unit),
            getDate(dto.dailyForecasts?.get(0)?.effectiveDate)
        )
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