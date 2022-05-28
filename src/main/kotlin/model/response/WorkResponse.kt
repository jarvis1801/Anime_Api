package com.jarvis.anime.api.model.response

import com.jarvis.anime.api.kmongo.model.core.Work
import com.jarvis.anime.api.model.response.base.BaseNameResponse

class WorkResponse(work: Work) : BaseNameResponse(work) {
    var thumbnail: ArrayList<String?>? = null
    var tag_list: List<TagResponse>? = null

    init {
        thumbnail = work.thumbnail_id_list
    }
}
