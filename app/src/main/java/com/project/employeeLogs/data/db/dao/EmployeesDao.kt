package com.project.employeeLogs.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.employeeLogs.data.db.entity.EmployeeEntity

@Dao
interface EmployeesDao {
    @Query("SELECT * FROM Employees")
     fun getAllEmployees(): List<EmployeeEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEmployees(employees: List<EmployeeEntity>)

    @Query("DELETE FROM Employees")
    suspend fun deleteAllEmployees()
}
