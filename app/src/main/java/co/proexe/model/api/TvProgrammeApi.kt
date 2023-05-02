package co.proexe.model.api

import co.proexe.model.data.TvProgramme
import retrofit2.http.GET

interface TvProgrammeApi {
    @GET("GG8C")
    suspend fun getTvProgrammes(): List<TvProgramme>
}