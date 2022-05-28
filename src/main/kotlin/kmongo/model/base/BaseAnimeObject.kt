package com.jarvis.anime.api.kmongo.model.base

abstract class BaseAnimeObject<T>(
    var extra_name: Translation? = null,
    var author_id_list: ArrayList<String>? = null,
    var work_id: String? = null,
    var ended: Boolean? = null,
    var volume_id_list: ArrayList<String>? = null
) : BaseObject<T>()

enum class AnimeType(val type: String) {
    NOVEL("novel"),
    MANGA("manga"),
    ANIME("anime")
}