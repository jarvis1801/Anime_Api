package com.jarvis.acg.api.model.response.base

import com.jarvis.acg.api.kmongo.model.base.BaseObject
import java.time.LocalDateTime

@Suppress("PropertyName")
abstract class BaseResponse(baseObject: BaseObject<*>) {
    var id: String? = null
    var created_at: LocalDateTime? = null
    var updated_at: LocalDateTime? = null

    init {
        id = baseObject._id
        created_at = baseObject.created_at
        updated_at = baseObject.updated_at
    }
}