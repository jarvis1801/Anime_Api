package com.jarvis.anime.api.kmongo.converter

import org.bson.json.Converter
import org.bson.json.StrictJsonWriter
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class JsonDateTimeConverter : Converter<Long?> {
    override fun convert(value: Long?, writer: StrictJsonWriter) {
        try {
            value?.let {
                val instant: Instant = Date(value).toInstant()
                val s = DATE_TIME_FORMATTER.format(instant)
                writer.writeString(s)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.of("UTC"))
    }
}