package com.jarvis.anime.api.kmongo.model.core

import com.jarvis.anime.api.kmongo.model.base.BaseAnimeObject
import java.util.*
import kotlin.collections.ArrayList

class Manga(
    var painter_id_list: ArrayList<String>? = null,
    var publishing_house_id_list: ArrayList<String>? = null,
    var publish_start_date: Date? = null,
    var publish_end_date: Date? = null
) : BaseAnimeObject<Manga>()