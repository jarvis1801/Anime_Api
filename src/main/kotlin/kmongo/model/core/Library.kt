package com.jarvis.anime.api.kmongo.model.core

import com.jarvis.anime.api.kmongo.model.base.BaseNameObject

data class Library(
    var country: String? = null,
) : BaseNameObject<Library>()