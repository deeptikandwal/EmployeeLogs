package com.project.weatherAroundTheWorld.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Forecast")
data class DailyForecastDomainModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val text:String,
    val dayIconPhrase:String,
    val nightIconPhrase:String,
    val minValue: String,
    val maxValue:String,
    val date:String
)