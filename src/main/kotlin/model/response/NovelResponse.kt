package com.jarvis.anime.api.model.response

import com.jarvis.anime.api.kmongo.model.core.Novel
import com.jarvis.anime.api.model.response.base.BaseAnimeResponse
import java.util.*

class NovelResponse(obj: Novel) : BaseAnimeResponse(obj) {
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