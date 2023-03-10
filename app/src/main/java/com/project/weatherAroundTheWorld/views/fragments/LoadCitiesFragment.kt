package com.project.weatherAroundTheWorld.views.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.domain.model.CitiesDomainModel
import com.project.weatherAroundTheWorld.R
import com.project.weatherAroundTheWorld.databinding.FragmentLoadCitiesBinding
import com.project.weatherAroundTheWorld.utils.AppConstants
import com.project.weatherAroundTheWorld.utils.DataResource
import com.project.weatherAroundTheWorld.utils.WeatherDataState
import com.project.weatherAroundTheWorld.views.adapter.CitiesAdapter
import com.project.weatherAroundTheWorld.views.viewmodel.CitiesListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoadCitiesFragment : Fragment(), CitiesAdapter.CustomOnClickListener {
    lateinit var fragmentLoadCitiesBinding: FragmentLoadCitiesBinding
    val mcitiesListViewModel: CitiesListViewModel by viewModels()
    val citiesAdapter = CitiesAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoadCitiesBinding.inflate(
        layoutInflater, container, false
    ).also {
        fragmentLoadCitiesBinding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObserver()
    }

    private fun setViews() {
        citiesAdapter.setCustomOnClickListenerForAdapter(this)
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
        with(fragmentLoadCitiesBinding) {
            recycler.apply {
                addItemDecoration(
                    DividerItemDecoration(
                        recycler.context,
                        (recycler.layoutManager as LinearLayoutManager).orientation
                    )
                )
                adapter = citiesAdapter
            }
            back.setOnClickListener {
               activity?.onBackPressed()
            }
        }
    }

    private fun setObserver() {

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mcitiesListViewModel.state.collect {
                    modifyUiBasedOnViewState(it)
                }
            }
        }
    }

    private fun modifyUiBasedOnViewState(dataResource: DataResource<List<CitiesDomainModel>>) {
        when (dataResource.status) {
            WeatherDataState.LOADING -> {
                with(fragmentLoadCitiesBinding) {
                    progress.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                }
            }

            WeatherDataState.SUCCESS -> {
                with(fragmentLoadCitiesBinding) {
                    progress.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                }
                updateList(dataResource.data)
            }

            WeatherDataState.ERROR -> {
                fragmentLoadCitiesBinding.progress.visibility = View.GONE
                Toast.makeText(context, dataResource.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun updateList(cities: List<CitiesDomainModel>?) {
        with(citiesAdapter) {
            setDataList(cities)
            notifyDataSetChanged()
        }
    }

    override fun onItemClick(key: String, city: String) {
        findNavController().navigate(
            R.id.homescreen_to_detailscreen,
            bundleOf(AppConstants.KEYFORCITY to key, AppConstants.CITY to city)
        )
    }


}