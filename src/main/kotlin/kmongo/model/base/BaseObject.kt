package com.jarvis.acg.api.kmongo.model.base

import com.jarvis.acg.api.util.DateTimeUtil
import kotlinx.serialization.Contextual
import org.bson.types.ObjectId
import org.litote.kmongo.id.toId
import java.time.LocalDateTime
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

abstract class BaseObject<T>(
    val _id: String = ObjectId().toId<T>().toString(),
    @Contextual val created_at: LocalDateTime = DateTimeUtil().getLocalTimeStamp(),
    @Contextual var updated_at: LocalDateTime = DateTimeUtil().getLocalTimeStamp()
) {

    private lateinit var objectValueMap: HashMap<String, Any>
    private lateinit var objectFileMap: HashMap<String, Any>

    fun convertMappingToProperty(obj: BaseObject<T>, mapping: HashMap<String, Any?>) : BaseObject<T> {
        mapping.forEach {(key, value) ->
            value?.let {
                obj[key] = value
            }
        }
        return obj
    }

    operator fun set(propName: String, value: Any?) {
        val property = this::class.memberProperties.find { it.name.equals(propName, true) }
        when (property) {
            is KMutableProperty<*> -> {
                setValue("${property.returnType}", property.setter, value)
            }
            null -> {}
            else -> {}
        }
    }

    private fun setValue(propertyTypeString: String, setter: KMutableProperty.Setter<out Any?>, value: Any?) {
        if (value.toString().isNotBlank()) {
            when {
                propertyTypeString.contains("Int?") -> setter.call(this, value.toString().toInt())
                propertyTypeString.contains("Boolean?") -> setter.call(this, value.toString().toBoolean())
                propertyTypeString.contains("List<kotlin.String>?") -> {
                    setter.call(this, value.toString().split(",").toCollection(ArrayList()))
                }
                propertyTypeString.contains("Date?") -> {
                    setter.call(this, DateTimeUtil().convertDateByString(value.toString()))
                }
                else -> setter.call(this, value)
            }
        }
    }
}