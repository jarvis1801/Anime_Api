package com.jarvis.acg.api.model.response

import com.jarvis.acg.api.kmongo.model.core.Work
import com.jarvis.acg.api.model.response.base.BaseNameResponse

class WorkResponse(work: Work) : BaseNameResponse(work) {
    var thumbnail: ArrayList<String?>? = null
    var tag_list: List<TagResponse>? = null

    init {
        thumbnail = work.thumbnail
    }
}
