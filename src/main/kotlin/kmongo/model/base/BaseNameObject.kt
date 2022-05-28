package com.jarvis.anime.api.kmongo.model.base

abstract class BaseNameObject<T>(
    var name: Translation? = null,
) : BaseObject<T>()