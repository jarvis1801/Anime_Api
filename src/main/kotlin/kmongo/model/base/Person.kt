package com.jarvis.anime.api.kmongo.model.base

abstract class Person<T>(
    var info: Translation? = null,
    var thumbnail: ArrayList<String?>? = null
) : BaseNameObject<T>()