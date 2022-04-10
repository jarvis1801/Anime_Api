package com.jarvis.acg.api.model.response

import com.jarvis.acg.api.kmongo.model.core.Chapter
import com.jarvis.acg.api.kmongo.model.base.Translation
import com.jarvis.acg.api.model.response.base.BaseNameResponse

class ChapterResponse(obj: Chapter) : BaseNameResponse(obj) {
    var sectionName: Translation? = null
    var content: Translation? = null
    var volume_id: String? = null
    var order: Int? = null

    init {
        sectionName = obj.sectionName
        content = obj.content
        volume_id = obj.volume_id
        order = obj.order
    }
}