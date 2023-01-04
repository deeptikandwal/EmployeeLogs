package com.project.weatherAroundTheWorld.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.project.weatherAroundTheWorld.R
import com.project.weatherAroundTheWorld.databinding.FragmentForecastBinding
import com.project.weatherAroundTheWorld.domain.model.DailyForecastDomainModel
import com.project.weatherAroundTheWorld.utils.AppConstants
import com.project.weatherAroundTheWorld.views.viewState.ForeCastState
import com.project.weatherAroundTheWorld.views.viewmodel.ForeCastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForeCastFragment : Fragment() {
    lateinit var fragmentForecastBinding: FragmentForecastBinding
    val animeViewModel: ForeCastViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        DataBindingUtil.inflate<FragmentForecastBinding?>(
            layoutInflater,
            R.layout.fragment_forecast,
            container,
            false
        ).also {
            fragmentForecastBinding = it
        }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            animeViewModel.getForeCastList(arguments?.getString(AppConstants.KEYFORCITY).toString())
        }


        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                animeViewModel.state.collect {
                    when (it) {
                        is ForeCastState.IDLE -> {
                            //no ops
                        }
                        is ForeCastState.LOADING -> {
                            with(fragmentForecastBinding) {
                                progress.visibility = View.VISIBLE
                            }
                        }

                        is ForeCastState.SUCCESS -> {
                            fragmentForecastBinding.run {
                                progress.visibility = View.GONE
                                updateUi(it.forcast.get(0))
                            }

                        }
                        is ForeCastState.ERROR -> {
                            fragmentForecastBinding.progress.visibility = View.GONE
                            Toast.makeText(context, it.error, Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }

    private fun updateUi(forecastDomainModel: DailyForecastDomainModel) {
        with(fragmentForecastBinding) {
            date.text = forecastDomainModel.date
            quote.text =
                arguments?.getString(AppConstants.CITY).plus(" : ").plus(forecastDomainModel.text)
            minValue.text =
                context?.getString(R.string.mintemperature).plus(forecastDomainModel.minValue)
            if (forecastDomainModel.hasprecipitation) maxValue.text =
                context?.getString(R.string.higlyprecipitated) else maxValue.text =context?.getString(R.string.noprecipitated)
        }
        Glide.with(this)
            .load(
                getResources()
                    .getIdentifier(getBitmap(forecastDomainModel), "drawable", context?.packageName)
            )
            .into(fragmentForecastBinding.imgview)
    }

    fun getBitmap(forecastDomainModel: DailyForecastDomainModel): String {
        if (!forecastDomainModel.isDayTime) {
            fragmentForecastBinding.dayNight.text =  context?.getString(R.string.nightsky)
            return "night_sky"
        } else{
            fragmentForecastBinding.dayNight.text =  context?.getString(R.string.daytime)
            return "day_sky"
        }
    }

    private fun setViews() {
       requireActivity().onBackPressedDispatcher.addCallback(object:OnBackPressedCallback(true){
           override fun handleOnBackPressed() {
               findNavController().navigateUp()
           }
       })
        with(fragmentForecastBinding) {
            back.setOnClickListener {
                findNavController().navigate(R.id.homescreen)
            }
        }
    }
}