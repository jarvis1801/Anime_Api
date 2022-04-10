package com.jarvis.acg.api.model.response.base

import com.jarvis.acg.api.kmongo.model.base.BaseNameObject
import com.jarvis.acg.api.kmongo.model.base.Translation

abstract class BaseNameResponse(baseNameObject: BaseNameObject<*>) : BaseResponse(baseNameObject) {
    var name: Translation? = null

    init {
        name = baseNameObject.name
    }
}