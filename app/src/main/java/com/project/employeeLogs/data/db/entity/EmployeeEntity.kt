package com.project.employeeLogs.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Employees")
data class EmployeeEntity(
    @PrimaryKey val id: Int?=null,
    val name: String?=null,
    val email: String?=null,
    val avatar: String?=null,
)
