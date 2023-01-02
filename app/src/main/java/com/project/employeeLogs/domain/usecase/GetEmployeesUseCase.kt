package com.project.employeeLogs.domain.usecase

import com.project.employeeLogs.domain.model.EmployeeDomainModel
import com.project.employeeLogs.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetEmployeesUseCase(private val employeeRepository: EmployeeRepository) {
    operator fun invoke(): Flow<List<EmployeeDomainModel>> {
        return employeeRepository.getEmployees()
    }

}