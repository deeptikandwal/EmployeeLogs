package com.project.employeeLogs.views.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.project.employeeLogs.databinding.AnimeListBinding
import com.project.employeeLogs.domain.model.AnimeDomainModel
import com.project.employeeLogs.views.viewHolder.AnimeViewHolder

class AnimeAdapter : RecyclerView.Adapter<AnimeViewHolder>() {
    var animeList= mutableListOf<AnimeDomainModel>()
    fun setDataList( animes: List<AnimeDomainModel>){
        animeList=animes as MutableList<AnimeDomainModel>
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeViewHolder = AnimeViewHolder( AnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: AnimeViewHolder, position: Int) {
        holder.bind(animeList[position])
    }

    override fun getItemCount(): Int {
        return animeList.size
    }
}

