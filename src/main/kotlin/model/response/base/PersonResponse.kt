package com.jarvis.acg.api.model.response.base

import com.jarvis.acg.api.kmongo.model.base.Person
import com.jarvis.acg.api.kmongo.model.base.Translation

abstract class PersonResponse(person: Person<*>) : BaseNameResponse(person) {
    var info: Translation? = null
    var thumbnail: ArrayList<String?>? = null

    init {
        info = person.info
        thumbnail = person.thumbnail
    }
}