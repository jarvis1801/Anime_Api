package com.jarvis.acg.api.kmongo.model.base

abstract class Media<T>(
    var url: String? = null,
    var order: Int? = null
) : BaseObject<T>()