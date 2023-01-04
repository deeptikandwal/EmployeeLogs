package com.project.weatherAroundTheWorld.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animes")
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int?=null,
    val anime: String? = null,
    val character: String?=null,
    val quote: String?=null
)