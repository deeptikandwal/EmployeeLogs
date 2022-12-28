package com.project.employeeLogs.views.viewState

import com.project.employeeLogs.domain.model.AnimeDomainModel

sealed class AnimeState{
    object IDLE:AnimeState()
    object LOADING:AnimeState()
    data class SUCCESS(val animes: List<AnimeDomainModel>) : AnimeState()
    data class ERROR(val error: String?) : AnimeState()
}
