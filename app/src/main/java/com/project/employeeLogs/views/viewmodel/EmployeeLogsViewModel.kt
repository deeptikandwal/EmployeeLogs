package com.project.employeeLogs.views.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.employeeLogs.domain.model.EmployeeDomainModel
import com.project.employeeLogs.domain.usecase.GetEmployeesUseCase
import com.project.employeeLogs.views.viewState.EmployeeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmployeeLogsViewModel @Inject constructor(
    var getEmployeesUseCase: GetEmployeesUseCase
) : ViewModel() {
    lateinit var result: List<EmployeeDomainModel>
    private val _state = MutableSharedFlow<EmployeeState>()
    val state: SharedFlow<EmployeeState>
        get() = _state

    init {
        handleOperation()
    }

    private fun handleOperation() {
        viewModelScope.launch {
            _state.emit(EmployeeState.LOADING)
            try {
                getEmployeesUseCase.invoke().also { flowEmployeeList ->
                    val list = getEmployeeListFromFlow(flowEmployeeList)
                    _state.emit(EmployeeState.SUCCESS(list))
                }
            } catch (exception: Exception) {
                _state.emit(EmployeeState.ERROR(exception.toString()))
            }
        }

    }

    private suspend fun getEmployeeListFromFlow(flowEmployeeList: Flow<List<EmployeeDomainModel>>)= flowEmployeeList.flatMapConcat { listEmployeeDomain -> listEmployeeDomain.asFlow() }.toList()


}
