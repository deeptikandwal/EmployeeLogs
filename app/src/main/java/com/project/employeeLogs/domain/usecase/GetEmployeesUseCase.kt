package com.project.employeeLogs.domain.usecase

import com.project.employeeLogs.domain.model.EmployeeDomainModel
import com.project.employeeLogs.domain.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow

class GetEmployeesUseCase(private val onScreenRepository: EmployeeRepository) {
    operator fun invoke(): Flow<List<EmployeeDomainModel>> {
        return onScreenRepository.getEmployees()
    }

}