package com.project.employeeLogs.data.repository

import com.project.employeeLogs.data.api.ApiService
import com.project.employeeLogs.data.db.EmployeeDb
import com.project.employeeLogs.data.mapper.EmployeeMapper
import com.project.employeeLogs.data.response.EmployeeListDto
import com.project.employeeLogs.domain.model.EmployeeDomainModel
import com.project.employeeLogs.domain.repository.EmployeeRepository
import com.project.employeeLogs.utils.ConnectionUtils
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EmployeeRepositoryImpl @Inject constructor(
    employeeDb: EmployeeDb,
    val apiService: ApiService,
    val mapper: EmployeeMapper,
    val dispatcher: CoroutineDispatcher,
    val connectionUtils: ConnectionUtils
) :
    EmployeeRepository {
    val dbDao = employeeDb.employeesDao()
    val flow = flow {
        if (connectionUtils.isNetworkAvailable()) {
            emitEmployees()
        } else {
            emit(getDomainEmployeeFromEntity())
        }
    }.flowOn(dispatcher)

    override fun getEmployees() = flow


    private fun getDomainEmployeeFromEntity() =
        mapper.mapToEmployeeDomainFromEntity(dbDao.getAllEmployees())

    private fun getDomainFromDto(result: List<EmployeeListDto>) =
        mapper.mapToEmployeeDomainFromDto(result)

    private suspend fun FlowCollector<List<EmployeeDomainModel>>.emitEmployees() {
         apiService.fetchEmployees().also {
           dtoList->  emit(getDomainFromDto(dtoList))
             with(dbDao) {
                 deleteAllEmployees()
                 insertEmployees(mapper.mapToEmployeeEntity(dtoList))
             }
         }

    }


}