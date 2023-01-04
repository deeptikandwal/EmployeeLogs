package com.project.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Forecast")
data class DailyForecastDomainModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val text:String,
    val minValue: String,
    val date:String,
    val isDayTime:Boolean,
    val hasprecipitation:Boolean
)