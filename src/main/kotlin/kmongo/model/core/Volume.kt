package com.jarvis.anime.api.kmongo.model.core

import com.jarvis.anime.api.kmongo.model.base.BaseNameObject
import com.jarvis.anime.api.kmongo.model.base.Translation

data class Volume(
    var order: Int? = null,
    var book_id: String? = null,
    var sticky_header: Translation? = null,
    var chapter_id_list: ArrayList<String>? = null
) : BaseNameObject<Volume>()