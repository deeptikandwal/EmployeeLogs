package com.project.weatherAroundTheWorld.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.weatherAroundTheWorld.databinding.CitiesListBinding
import com.project.weatherAroundTheWorld.domain.model.CitiesDomainModel
import com.project.weatherAroundTheWorld.views.viewHolder.CitilesListViewHolder

class CitiesAdapter(val onItemClickListener: OnClickListener) : RecyclerView.Adapter<CitilesListViewHolder>() {
    var citiesList= mutableListOf<CitiesDomainModel>()
    interface OnClickListener {
        fun onItemClick(key:String, city:String)
    }
     fun setDataList(employees:List<CitiesDomainModel>){
        citiesList=employees as MutableList<CitiesDomainModel>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitilesListViewHolder = CitilesListViewHolder(CitiesListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: CitilesListViewHolder, position: Int) {
        holder.bind(citiesList[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return citiesList.size
    }


}