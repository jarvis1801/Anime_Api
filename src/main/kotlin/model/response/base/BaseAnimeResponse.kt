package com.jarvis.anime.api.model.response.base

import com.jarvis.anime.api.kmongo.model.base.BaseAnimeObject
import com.jarvis.anime.api.kmongo.model.base.Translation
import com.jarvis.anime.api.model.response.AuthorResponse
import com.jarvis.anime.api.model.response.WorkResponse

abstract class BaseAnimeResponse(obj: BaseAnimeObject<*>) : BaseResponse(obj) {
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