package com.project.weatherAroundTheWorld.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cities")
data class CitiesDomainModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val key:String,
    val region:String,
    val city:String,

)