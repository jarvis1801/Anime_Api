package com.jarvis.acg.api.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jarvis.acg.api.kmongo.converter.JsonDateTimeConverter
import com.jarvis.acg.api.kmongo.model.base.BaseObject
import org.bson.json.JsonWriterSettings
import org.litote.kmongo.bson
import org.litote.kmongo.json

object ExtensionUtil {
    inline fun <reified T> Gson.fromJson(json: String): T = fromJson(json, object: TypeToken<T>() {}.type)

    fun <T : BaseObject<T>> T.getResponse() : String {
        return json.bson.toJson(JsonWriterSettings.builder().dateTimeConverter(JsonDateTimeConverter()).build())
    }

    fun <T : BaseObject<T>> List<T>.getResponse() : String {
        val list = arrayListOf<String>()
        forEach {
            list.add(it.json.bson.toJson(JsonWriterSettings.builder().dateTimeConverter(JsonDateTimeConverter()).build()))
        }
        return list.toString()
    }

    fun String.replaceWhiteSpace(newValue: String = ""): String {
        return replace("　+".toRegex(), newValue)
    }

    fun String.replaceBreakLine(newValue: String = "\n"): String {
        return replace("\\n+".toRegex(), newValue)
    }
}