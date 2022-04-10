package com.jarvis.acg.api.util

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class DateTimeUtil {

    companion object {
        const val FORM_DATA_FORMAT = "yyyy/MM/dd"
    }

    fun getLocalTimeStamp() : LocalDateTime {
        return LocalDateTime.now()
    }

    fun convertDateByString(dateString: String, format: String? = FORM_DATA_FORMAT) : Date? {
        return if (dateString.isNotBlank())
            SimpleDateFormat(format!!).apply{ timeZone = TimeZone.getTimeZone("UTC") }.parse(dateString)
        else null
    }
}