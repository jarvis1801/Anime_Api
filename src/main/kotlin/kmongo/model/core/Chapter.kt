package com.jarvis.anime.api.kmongo.model.core

import com.jarvis.anime.api.kmongo.model.base.BaseNameObject
import com.jarvis.anime.api.kmongo.model.base.Translation

data class Chapter(
    var sectionName: Translation? = null,
    var content: Translation? = null,
    var volume_id: String? = null,
    var order: Int? = null,
): BaseNameObject<Chapter>()