package com.project.employeeLogs.views.viewHolder
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.employeeLogs.databinding.EmployeeListBinding
import com.project.employeeLogs.domain.model.EmployeeDomainModel

class EmployeeLogsViewHolder(val employeeListBinding: EmployeeListBinding) :
    RecyclerView.ViewHolder(employeeListBinding.root) {
    fun bind(item: EmployeeDomainModel, fn: () -> Unit) {
        with(employeeListBinding){
            textViewUserName.text = item.name
            Glide.with(imageViewAvatar.context)
                .load(item.avatar)
                .into(imageViewAvatar)
            textViewUserEmail.text = item.email
            next.setOnClickListener {
                fn.invoke()
            }
        }
    }
}