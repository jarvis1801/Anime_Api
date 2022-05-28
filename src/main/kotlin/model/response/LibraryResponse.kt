package com.jarvis.anime.api.model.response

import com.jarvis.anime.api.kmongo.model.core.Library
import com.jarvis.anime.api.model.response.base.BaseNameResponse

class LibraryResponse(obj: Library) : BaseNameResponse(obj) {
    var country: String? = null

    init {
        country = obj.country
    }
}