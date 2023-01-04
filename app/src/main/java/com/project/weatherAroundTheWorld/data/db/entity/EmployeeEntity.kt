package com.project.weatherAroundTheWorld.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cities")
data class EmployeeEntity(
    @PrimaryKey val id: Int?=null,
    val name: String?=null,
    val email: String?=null,
    val avatar: String?=null,
)
