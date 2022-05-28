package com.jarvis.anime.api.kmongo.model.core

import com.jarvis.anime.api.kmongo.model.base.BaseNameObject
import com.jarvis.anime.api.kmongo.model.base.Translation

class MangaChapter(
    var sectionName: Translation? = null,
    var image_id_list: ArrayList<String>? = null,
    var volume_id: String? = null,
    var order: Int? = null,
): BaseNameObject<MangaChapter>()