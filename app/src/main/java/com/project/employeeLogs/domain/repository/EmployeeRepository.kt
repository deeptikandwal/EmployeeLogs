package com.project.employeeLogs.domain.repository

import com.project.employeeLogs.domain.model.EmployeeDomainModel
import kotlinx.coroutines.flow.Flow

interface EmployeeRepository {
    fun getEmployees(): Flow<List<EmployeeDomainModel>>
}