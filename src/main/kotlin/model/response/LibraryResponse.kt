package com.jarvis.acg.api.model.response

import com.jarvis.acg.api.kmongo.model.core.Library
import com.jarvis.acg.api.model.response.base.BaseNameResponse

class LibraryResponse(obj: Library) : BaseNameResponse(obj) {
    var country: String? = null

    init {
        country = obj.country
    }
}