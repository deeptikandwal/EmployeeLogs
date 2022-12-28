package com.project.employeeLogs.views.viewState
import com.project.employeeLogs.domain.model.EmployeeDomainModel

sealed class EmployeeState{
    object IDLE:EmployeeState()
    object LOADING:EmployeeState()
    data class SUCCESS(val user: List<EmployeeDomainModel>) : EmployeeState()
    data class ERROR(val error: String?) : EmployeeState()
}
