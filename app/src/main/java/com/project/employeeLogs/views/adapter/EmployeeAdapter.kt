package com.project.employeeLogs.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.employeeLogs.databinding.EmployeeListBinding
import com.project.employeeLogs.domain.model.EmployeeDomainModel
import com.project.employeeLogs.views.viewHolder.EmployeeLogsViewHolder

class EmployeeAdapter(val fn: () -> Unit) : RecyclerView.Adapter<EmployeeLogsViewHolder>() {
    var employeeList= mutableListOf<EmployeeDomainModel>()

     fun setDataList(employees:List<EmployeeDomainModel>){
        employeeList=employees as MutableList<EmployeeDomainModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeLogsViewHolder = EmployeeLogsViewHolder(EmployeeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: EmployeeLogsViewHolder, position: Int) {
        holder.bind(employeeList[position], fn)
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }


}