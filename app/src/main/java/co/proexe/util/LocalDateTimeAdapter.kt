package co.proexe.util

import com.squareup.moshi.*
import java.time.LocalDateTime

import java.time.format.DateTimeFormatter

class LocalDateTimeAdapter {
    @ToJson
    fun toJson(value: LocalDateTime): String {
        return FORMATTER.format(value)
    }
    @FromJson
    fun fromJson(value: String): LocalDateTime {
        return LocalDateTime.from(FORMATTER.parse(value))
    }
    companion object {
        private val FORMATTER = DateTimeFormatter.ofPattern("EEE MMM dd H:mm:ss ZZZZ yyyy")
    }

}