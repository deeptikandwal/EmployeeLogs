package com.project.employeeLogs.views.viewmodel

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.employeeLogs.domain.usecase.GetAnimesUseCase
import com.project.employeeLogs.views.viewState.AnimeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AnimeViewModel @Inject constructor(var getAnimesUseCase: GetAnimesUseCase) :
    ViewModel() {
    var _state = MutableSharedFlow<AnimeState>()
    val state: SharedFlow<AnimeState>
        get() = _state
    val title = MutableLiveData<String?>()


    fun getAnimeList() {
        viewModelScope.launch {
            _state.emit(AnimeState.LOADING)
            try {
                val result = fetchAnimeList()
                _state.emit(AnimeState.SUCCESS(result))
            } catch (e: Exception) {
                _state.emit(AnimeState.ERROR(e.toString()))
            }
        }

    }

    private suspend fun fetchAnimeList() =
        getAnimesUseCase.invoke(title.value.toString()).flatMapConcat { listAnimeDomain ->
            listAnimeDomain.asFlow()
        }.toList()

    val onQueryTextListener: SearchView.OnQueryTextListener =
        object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                title.value = query
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText?.length==0) {
                    viewModelScope.launch {
                        _state.emit(AnimeState.IDLE)
                    }
                }
                return true
            }

        }

}