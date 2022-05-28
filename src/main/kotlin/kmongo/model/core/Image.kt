package com.jarvis.anime.api.kmongo.model.core

import com.jarvis.anime.api.kmongo.model.base.Media

class Image(
    var imageWidth: Int? = null,
    var imageHeight: Int? = null,
    var chapter_id: String? = null
) : Media<Image>()