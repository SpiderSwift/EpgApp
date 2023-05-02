package co.proexe.vm

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.proexe.model.data.TvProgramme
import co.proexe.model.repository.TimeRepository
import co.proexe.model.repository.TvProgrammeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class EpgViewModel: ViewModel() {

    private val tvRepo = TvProgrammeRepository()
    val timeRepo = TimeRepository()

    val tvProgrammesList = mutableStateListOf<TvProgramme>()
    private val _tvProgrammeFlow = MutableStateFlow(tvProgrammesList)

    val tvProgrammeFlow: StateFlow<List<TvProgramme>> get() = _tvProgrammeFlow

    fun setFavourite(index: Int, value: Boolean) {
        viewModelScope.launch {

            tvProgrammesList[index] = tvProgrammesList[index].copy(
                isFavourite = value,
                favouriteTimestamp = if (value) Date().time else null
            )
            tvProgrammesList.sortBy {
                !it.isFavourite
            }
            _tvProgrammeFlow.emit(tvProgrammesList)
        }
    }


    fun updateList() {
        viewModelScope.launch {
            tvProgrammesList.addAll(tvRepo.getTvProgrammes().toTypedArray())
            _tvProgrammeFlow.emit(tvProgrammesList)
        }
    }

}