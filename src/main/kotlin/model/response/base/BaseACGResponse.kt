package com.jarvis.acg.api.model.response.base

import com.jarvis.acg.api.kmongo.model.base.BaseACGObject
import com.jarvis.acg.api.kmongo.model.base.Translation
import com.jarvis.acg.api.model.response.AuthorResponse
import com.jarvis.acg.api.model.response.WorkResponse

abstract class BaseACGResponse(obj: BaseACGObject<*>) : BaseResponse(obj) {
    var extra_name: Translation? = null
    var author_list: List<AuthorResponse>? = null
    var work: WorkResponse? = null
    var ended: Boolean? = null
    var volume_id_list: ArrayList<String>? = null

    init {
        extra_name = obj.extra_name
        ended = obj.ended
        volume_id_list = obj.volume_id_list
    }
}