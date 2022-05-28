package com.jarvis.anime.api.kmongo.model.base

abstract class Media<T>(
    var url: String? = null,
    var order: Int? = null,
    var fileSize: Int? = null
) : BaseObject<T>()