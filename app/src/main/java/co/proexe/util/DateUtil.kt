package co.proexe.util

import java.time.ZoneId
import java.util.*

fun isSameDay(first: Date, other: Date): Boolean {
    return first.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        .isEqual(other.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
}