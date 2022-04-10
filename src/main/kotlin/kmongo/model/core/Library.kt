package com.jarvis.acg.api.kmongo.model.core

import com.jarvis.acg.api.kmongo.model.base.BaseNameObject

data class Library(
    var country: String? = null,
) : BaseNameObject<Library>()