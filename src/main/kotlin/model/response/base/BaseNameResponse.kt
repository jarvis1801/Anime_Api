package com.jarvis.anime.api.model.response.base

import com.jarvis.anime.api.kmongo.model.base.BaseNameObject
import com.jarvis.anime.api.kmongo.model.base.Translation

abstract class BaseNameResponse(baseNameObject: BaseNameObject<*>) : BaseResponse(baseNameObject) {
    var name: Translation? = null

    init {
        name = baseNameObject.name
    }
}