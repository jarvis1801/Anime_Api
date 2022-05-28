package com.jarvis.anime.api.kmongo.model.base

import com.jarvis.anime.api.kmongo.model.core.PublishingHouse

class BaseBookObject<T>(
    var painter_id: String? = null,
    var publishing_house_id_list: ArrayList<PublishingHouse>? = null,

) : BaseAnimeObject<T>()