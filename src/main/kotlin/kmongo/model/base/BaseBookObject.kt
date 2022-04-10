package com.jarvis.acg.api.kmongo.model.base

import com.jarvis.acg.api.kmongo.model.core.PublishingHouse

class BaseBookObject<T>(
    var painter_id: String? = null,
    var publishing_house_id_list: ArrayList<PublishingHouse>? = null,

) : BaseACGObject<T>()