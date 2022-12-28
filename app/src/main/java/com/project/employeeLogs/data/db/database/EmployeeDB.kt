package com.project.employeeLogs.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.employeeLogs.data.db.dao.AnimeDao
import com.project.employeeLogs.data.db.dao.EmployeesDao
import com.project.employeeLogs.data.db.entity.AnimeEntity
import com.project.employeeLogs.data.db.entity.EmployeeEntity

@Database(entities = [AnimeEntity::class, EmployeeEntity::class], version = 1, exportSchema = false)
abstract class EmployeeDb: RoomDatabase() {
    abstract fun employeesDao(): EmployeesDao
    abstract fun animesDao(): AnimeDao
}