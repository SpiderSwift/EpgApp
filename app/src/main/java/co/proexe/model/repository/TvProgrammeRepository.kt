package co.proexe.model.repository

import co.proexe.model.api.TvProgrammeApi
import co.proexe.model.data.TvProgramme
import co.proexe.model.data.TvProgrammeCategory
import co.proexe.util.LocalDateTimeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.HttpsURLConnection


class TvProgrammeRepository {

    private val client = OkHttpClient.Builder()
        .hostnameVerifier { _, _ ->
            true
        }.build()

    private val retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl("https://jsonkeeper.com/b/")
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder()
                    .add(LocalDateTimeAdapter())
                    .build()
            )
        )
        .build()


    private val adapter = LocalDateTimeAdapter()

    // TODO: remove test mock
    private val mockList = listOf(
        TvProgramme(1, "Pojedynek", "https://www.dropbox.com/s/3lzgp0nmmku61d6/channel_logo_06.png?dl=1", "Kryminał", TvProgrammeCategory.KIDS, false,  null, adapter.fromJson("Tue May 02 11:11:52 GMT+02:00 2023"), adapter.fromJson("Tue May 02 13:45:52 GMT+02:00 2023"), 48),
        TvProgramme(2, "Wikingowie, odc. 5, sezon 8", "https://www.dropbox.com/s/mkqh6xp1i6a93vb/channel_logo_08.png?dl=1", "Historyczny", TvProgrammeCategory.EDUCATIONAL, false,  null,adapter.fromJson("Tue May 02 12:02:52 GMT+02:00 2023"), adapter.fromJson("Tue May 02 12:35:52 GMT+02:00 2023"), 72),
        TvProgramme(3, "Harry Potter i insygnia śmierci", "https://www.dropbox.com/s/jxyd3rjbph6w1uq/channel_logo_07.png?dl=1", "Fantasy", TvProgrammeCategory.MOVIES_AND_SERIES, false, null, adapter.fromJson("Mon May 01 11:17:52 GMT+02:00 2023"), adapter.fromJson("Mon May 01 13:58:52 GMT+02:00 2023"), 42),
        TvProgramme(4, "Zabójcza broń 3", "https://www.dropbox.com/s/4i03hp3jidbfsiu/channel_logo_05.png?dl=1", "Akcja", TvProgrammeCategory.INFO, false,  null,adapter.fromJson("Mon May 01 12:15:52 GMT+02:00 2023"), adapter.fromJson("Mon May 01 12:43:52 GMT+02:00 2023"), 39),
        TvProgramme(5, "Skazani na Shawshank", "https://www.dropbox.com/s/mfbmcgvi14v37df/channel_logo_04.png?dl=1", "Dramat", TvProgrammeCategory.MUSIC, false, null, adapter.fromJson("Sun Apr 30 10:58:52 GMT+02:00 2023"), adapter.fromJson("Sun Apr 30 14:04:52 GMT+02:00 2023"), 47),
        TvProgramme(6, "Ojciec chrzestny", "https://www.dropbox.com/s/glmjpxc3u2not12/channel_logo_03.png?dl=1", "Dokumentalny", TvProgrammeCategory.GENERAL, false, null,adapter.fromJson("Sun Apr 30 12:10:52 GMT+02:00 2023"), adapter.fromJson("Sun Apr 30 12:39:52 GMT+02:00 2023"), 55),
        TvProgramme(7, "Mroczny rycerz", "https://www.dropbox.com/s/4vellphu7o8gyob/channel_logo_02.png?dl=1", "Fabularny", TvProgrammeCategory.SPORT, false, null, adapter.fromJson("Wed May 03 11:04:52 GMT+02:00 2023"), adapter.fromJson("Wed May 03 13:44:52 GMT+02:00 2023"), 51),
        TvProgramme(8, "Hobbit", "https://www.dropbox.com/s/zfjplpzz9k1udlv/channel_logo_01.png?dl=1", "Przygodowy", TvProgrammeCategory.LIFESTYLE, false, null, adapter.fromJson("Wed May 03 11:55:52 GMT+02:00 2023"), adapter.fromJson("Wed May 03 13:04:52 GMT+02:00 2023"), 44),
        TvProgramme(9, "Ukryta prawda", "https://www.dropbox.com/s/9u7kw2knftcur8v/channel_logo_09.png?dl=1", "Serial paradokumentalny", TvProgrammeCategory.ALL,  false,null, adapter.fromJson("Thu May 04 10:55:52 GMT+02:00 2023"), adapter.fromJson("Thu May 04 14:06:52 GMT+02:00 2023"), 47),
        TvProgramme(10, "Pojedynek", "https://www.dropbox.com/s/3lzgp0nmmku61d6/channel_logo_06.png?dl=1", "Kryminał", TvProgrammeCategory.KIDS, false, null,adapter.fromJson("Thu May 04 11:11:52 GMT+02:00 2023"), adapter.fromJson("Thu May 04 13:45:52 GMT+02:00 2023"), 48)
    )

    private val tvProgrammeApi = retrofit.create(TvProgrammeApi::class.java)

    suspend fun getTvProgrammes() : List<TvProgramme> {
        // TODO: return list from API
        val list = tvProgrammeApi.getTvProgrammes()
        return mockList
    }

}