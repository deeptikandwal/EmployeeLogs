package com.project.employeeLogs.data.response
import com.google.gson.annotations.SerializedName
data class EmployeeListDto(
     val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String ,
    @SerializedName("avatar")
    val avatar: String,
)