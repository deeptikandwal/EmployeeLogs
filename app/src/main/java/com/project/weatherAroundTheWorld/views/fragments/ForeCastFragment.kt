package com.project.weatherAroundTheWorld.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.project.domain.model.DailyForecastDomainModel
import com.project.weatherAroundTheWorld.R
import com.project.weatherAroundTheWorld.databinding.FragmentForecastBinding
import com.project.weatherAroundTheWorld.utils.AppConstants
import com.project.weatherAroundTheWorld.utils.WeatherDataState
import com.project.weatherAroundTheWorld.views.viewmodel.ForeCastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ForeCastFragment : Fragment() {
    lateinit var fragmentForecastBinding: FragmentForecastBinding
    val animeViewModel: ForeCastViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentForecastBinding.inflate(layoutInflater, container, false).also {
            fragmentForecastBinding = it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launch {
            animeViewModel.getForeCastList(arguments?.getString(AppConstants.KEYFORCITY).toString())
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animeViewModel.state.collect {
                    when (it.status) {
                        WeatherDataState.LOADING -> {
                            with(fragmentForecastBinding) {
                                withContext(Dispatchers.Main) {
                                    progress.visibility = View.VISIBLE
                                }
                            }
                        }

                        WeatherDataState.SUCCESS -> {
                            fragmentForecastBinding.run {
                                updateUi(it.data?.get(0))
                                progress.visibility = View.GONE
                            }

                        }
                        WeatherDataState.ERROR -> {
                            with(fragmentForecastBinding) {
                                nodata.visibility = View.VISIBLE
                                mainLayout.visibility = View.GONE
                                progress.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateUi(forecastDomainModel: DailyForecastDomainModel?) {
        with(fragmentForecastBinding) {
            date.text = forecastDomainModel?.date
            quote.text = arguments?.getString(AppConstants.CITY).plus(" : ").plus(forecastDomainModel?.text)
            minValue.text = context?.getString(R.string.mintemperature).plus(forecastDomainModel?.currentTemperature)
            if (forecastDomainModel?.hasprecipitation == true) maxValue.text =
                context?.getString(R.string.precipitation).plus(" : ").plus(context?.getString(R.string.higlyprecipitated))
            else maxValue.text = context?.getString(R.string.precipitation).plus(" : ").plus(context?.getString(R.string.noprecipitated))
        }.also {
            Glide.with(this)
                .load(
                    getResources()
                        .getIdentifier(
                            getBitmap(forecastDomainModel),
                            "drawable",
                            context?.packageName
                        )
                )
                .into(fragmentForecastBinding.imgview)
        }

    }

    fun getBitmap(forecastDomainModel: DailyForecastDomainModel?): String {
        if (forecastDomainModel?.isDayTime == false) {
            fragmentForecastBinding.dayNight.text = context?.getString(R.string.nightsky)
            return "night_sky"
        } else {
            fragmentForecastBinding.dayNight.text = context?.getString(R.string.daytime)
            return "day_sky"
        }
    }

    private fun setViews() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(R.id.homescreen)
            }
        })
        with(fragmentForecastBinding) {
            back.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}