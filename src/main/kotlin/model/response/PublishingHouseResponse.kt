package com.jarvis.acg.api.model.response

import com.jarvis.acg.api.kmongo.model.core.PublishingHouse
import com.jarvis.acg.api.model.response.base.BaseNameResponse

class PublishingHouseResponse(obj: PublishingHouse) : BaseNameResponse(obj) {
    var country: String? = null

    init {
        country = obj.country
    }
}