package com.jarvis.acg.api.kmongo.model.core

import com.jarvis.acg.api.kmongo.model.base.BaseNameObject

data class Work(
    var thumbnail: ArrayList<String?>? = null,
    var tag_id_list: ArrayList<String>? = null,
) : BaseNameObject<Work>()