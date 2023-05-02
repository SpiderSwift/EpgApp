package co.proexe.model.data

import java.time.LocalDateTime

data class TvProgramme(
    val id: Int,
    val title: String,
    val imageUrl: String,
    val type: String,
    val category: TvProgrammeCategory,
    val isFavourite: Boolean,
    val favouriteTimestamp: Long?,
    var startTimeDateRaw: LocalDateTime,
    var endTimeDateRaw: LocalDateTime,
    val progressPercent: Int
) : Comparable<TvProgramme> {
    override fun compareTo(other: TvProgramme): Int {
        return compareValuesBy(
            this,
            other,
            { it.favouriteTimestamp },
            { it.isFavourite },
            { it.id } // TODO: rating should be existent in some way
        )
    }

}