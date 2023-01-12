package com.project.weatherAroundTheWorld.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.domain.model.CitiesDomainModel
import com.project.weatherAroundTheWorld.databinding.CitiesListBinding
import com.project.weatherAroundTheWorld.views.viewHolder.CitilesListViewHolder

class CitiesAdapter : RecyclerView.Adapter<CitilesListViewHolder>() {
    var citiesList = mutableListOf<CitiesDomainModel>()
    lateinit var customOnClickListener: CustomOnClickListener

    interface CustomOnClickListener {
        fun onItemClick(key: String, city: String)
    }

    fun setDataList(employees: List<CitiesDomainModel>?) {
        citiesList = employees as MutableList<CitiesDomainModel>
    }

    fun setCustomOnClickListenerForAdapter(customOnClickListener: CustomOnClickListener) {
        this.customOnClickListener = customOnClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitilesListViewHolder =
        CitilesListViewHolder(
            CitiesListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CitilesListViewHolder, position: Int) {
        holder.bind(citiesList[position], customOnClickListener)
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }


}