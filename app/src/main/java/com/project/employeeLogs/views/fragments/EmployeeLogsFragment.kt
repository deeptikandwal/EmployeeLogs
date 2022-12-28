package com.project.employeeLogs.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.employeeLogs.R
import com.project.employeeLogs.databinding.FragmentEmployeeLogsBinding
import com.project.employeeLogs.domain.model.EmployeeDomainModel
import com.project.employeeLogs.views.adapter.EmployeeAdapter
import com.project.employeeLogs.views.viewState.EmployeeState
import com.project.employeeLogs.views.viewmodel.EmployeeLogsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeLogsFragment : Fragment() {
    lateinit var fragmentEmployeeLogsBinding: FragmentEmployeeLogsBinding
    val mhomescreenViewModel: EmployeeLogsViewModel by viewModels()
    val employeeAdapter = EmployeeAdapter{ findNavController().navigate(R.id.homescreen_to_detailscreen) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = DataBindingUtil.inflate<FragmentEmployeeLogsBinding>(layoutInflater,R.layout.fragment_employee_logs,container,false).also {
        fragmentEmployeeLogsBinding =it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObserver()
    }

    private fun setViews() {
        with(fragmentEmployeeLogsBinding){
            recycler.run {
                addItemDecoration(DividerItemDecoration(recycler.context, (recycler.layoutManager as LinearLayoutManager).orientation))
                adapter=employeeAdapter
            }
            back.setOnClickListener {
                activity?.finish()
            }
            }


    }

    private fun setObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mhomescreenViewModel.state.collect {
                    modifyUiBasedOnViewState(it)
                }
            }
        }
    }

    private fun modifyUiBasedOnViewState(it: EmployeeState) {
        when (it) {
            is EmployeeState.IDLE -> {
                fragmentEmployeeLogsBinding.progress.visibility = View.GONE
            }
            is EmployeeState.LOADING -> {
                with(fragmentEmployeeLogsBinding) {
                    progress.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                }
            }

            is EmployeeState.SUCCESS -> {
                with(fragmentEmployeeLogsBinding) {
                    progress.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                }
                updateList(it.user)
            }

            is EmployeeState.ERROR -> {
                fragmentEmployeeLogsBinding.progress.visibility = View.GONE
                Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateList(users: List<EmployeeDomainModel>) {
        with(employeeAdapter){
            setDataList(users)
            notifyDataSetChanged()
        }
    }


}