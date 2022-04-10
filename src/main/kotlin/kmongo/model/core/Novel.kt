package com.jarvis.acg.api.kmongo.model.core

import com.jarvis.acg.api.kmongo.model.base.BaseACGObject
import java.util.*
import kotlin.collections.ArrayList

class Novel(
    var painter_id_list: ArrayList<String>? = null,
    var publishing_house_id_list: ArrayList<String>? = null,
    var library_id_list: ArrayList<String>? = null,
    var publish_start_date: Date? = null,
    var publish_end_date: Date? = null
) : BaseACGObject<Novel>()