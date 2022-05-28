package com.jarvis.anime.api.model.response

import com.jarvis.anime.api.kmongo.model.core.PublishingHouse
import com.jarvis.anime.api.model.response.base.BaseNameResponse

class PublishingHouseResponse(obj: PublishingHouse) : BaseNameResponse(obj) {
    var country: String? = null

    init {
        country = obj.country
    }
}