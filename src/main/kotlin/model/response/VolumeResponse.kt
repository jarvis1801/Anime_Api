package com.jarvis.anime.api.model.response

import com.jarvis.anime.api.kmongo.model.base.Translation
import com.jarvis.anime.api.kmongo.model.core.Volume
import com.jarvis.anime.api.model.response.base.BaseNameResponse

class VolumeResponse(obj: Volume) : BaseNameResponse(obj) {
    var order: Int? = null
    var book_id: String? = null
    var sticky_header: Translation? = null
    var chapter_list: List<ChapterResponse>? = null

    init {
        order = obj.order
        book_id = obj.book_id
        sticky_header = obj.sticky_header
    }
}