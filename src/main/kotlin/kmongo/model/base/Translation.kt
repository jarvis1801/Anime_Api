package com.jarvis.acg.api.kmongo.model.base

import kotlinx.serialization.Serializable
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

@Serializable
data class Translation(
    var tc: String? = null,
    var en: String? = null,
    var jp: String? = null,
    var ro: String? = null
) {
    operator fun set(propName: String, value: Any) {
        val property = this::class.memberProperties.find { it.name == propName }
        when (property) {
            is KMutableProperty<*> -> property.setter.call(this, value)
            else -> {}
        }
    }
}

