package com.jarvis.acg.api.model.response

import com.jarvis.acg.api.kmongo.model.base.Translation
import com.jarvis.acg.api.kmongo.model.core.Author
import com.jarvis.acg.api.model.response.base.BaseNameResponse

class AuthorResponse(author: Author) : BaseNameResponse(author) {
    var thumbnail: ArrayList<String?>? = null
    var info: Translation? = null

    init {
        thumbnail = author.thumbnail
        info = author.info
    }
}