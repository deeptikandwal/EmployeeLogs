package com.project.employeeLogs.data.mapper

import com.project.employeeLogs.data.db.entity.EmployeeEntity
import com.project.employeeLogs.data.response.EmployeeListDto
import com.project.employeeLogs.domain.model.EmployeeDomainModel

class EmployeeMapper {
    fun mapToEmployeeDomainFromDto(itemsdto: List<EmployeeListDto>): List<EmployeeDomainModel> =
        itemsdto.map { employeeDto ->
            EmployeeDomainModel(
                employeeDto.id, employeeDto.name, employeeDto.email, employeeDto.avatar
            )
        }

    fun mapToEmployeeDomainFromEntity(itemsEntity: List<EmployeeEntity>): List<EmployeeDomainModel> =
        itemsEntity.map { employeeEntity ->
            EmployeeDomainModel(
                employeeEntity.id ?: 0,
                employeeEntity.name.orEmpty(),
                employeeEntity.email.orEmpty(),
                employeeEntity.avatar.orEmpty()
            )
        }

    fun mapToEmployeeEntity(itemsdto: List<EmployeeListDto>): List<EmployeeEntity> =
        itemsdto.map { employeeDto ->
            EmployeeEntity(employeeDto.id, employeeDto.name, employeeDto.email, employeeDto.avatar)
        }


}