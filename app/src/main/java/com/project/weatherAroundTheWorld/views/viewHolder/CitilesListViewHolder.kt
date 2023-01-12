package com.project.weatherAroundTheWorld.views.viewHolder
import androidx.recyclerview.widget.RecyclerView
import com.project.weatherAroundTheWorld.databinding.CitiesListBinding
import com.project.domain.model.CitiesDomainModel
import com.project.weatherAroundTheWorld.views.adapter.CitiesAdapter

class CitilesListViewHolder(val citiesListBinding: CitiesListBinding) :
    RecyclerView.ViewHolder(citiesListBinding.root) {
    fun bind(item: CitiesDomainModel, customOnClickListener:CitiesAdapter.CustomOnClickListener) {
        with(citiesListBinding){
            textViewCityName.text = item.city
            textViewRegion.text=item.region
            container.setOnClickListener {
                customOnClickListener.onItemClick(item.key,item.city)
            }
        }
    }
}