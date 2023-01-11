package com.project.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "Forecast")
class DailyForecastEntity(
    @PrimaryKey(autoGenerate = true)
                             val id:Int=0,
                             val keyForCity:String,
                             val text:String,
                             val minValue: String,
                             val date:String,
                             val isDayTime:Boolean,
                             val hasprecipitation:Boolean) {
}