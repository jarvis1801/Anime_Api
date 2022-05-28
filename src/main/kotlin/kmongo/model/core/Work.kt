package com.jarvis.anime.api.kmongo.model.core

import com.jarvis.anime.api.kmongo.model.base.BaseNameObject

data class Work(
    var thumbnail_id_list: ArrayList<String?>? = null,
    var tag_id_list: ArrayList<String>? = null,
) : BaseNameObject<Work>()