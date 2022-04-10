package com.jarvis.acg.api.model.response

import com.jarvis.acg.api.kmongo.model.base.BaseACGObject
import com.jarvis.acg.api.kmongo.model.core.Library
import com.jarvis.acg.api.kmongo.model.core.Novel
import com.jarvis.acg.api.model.response.base.BaseACGResponse
import java.util.*
import kotlin.collections.ArrayList

class NovelResponse(obj: Novel) : BaseACGResponse(obj) {
    var painter_list: List<PainterResponse>? = null
    var publishing_house_list: List<PublishingHouseResponse>? = null
    var library_list: List<LibraryResponse>? = null
    var publish_start_date: Date? = null
    var publish_end_date: Date? = null

    init {
        publish_start_date = obj.publish_start_date
        publish_end_date = obj.publish_end_date
    }
}