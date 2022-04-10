package com.jarvis.acg.api.kmongo.model.base

abstract class BaseNameObject<T>(
    var name: Translation? = null,
) : BaseObject<T>()