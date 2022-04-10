package com.jarvis.acg.api.kmongo.model.base

abstract class BaseACGObject<T>(
    var extra_name: Translation? = null,
    var author_id_list: ArrayList<String>? = null,
    var work_id: String? = null,
    var ended: Boolean? = null,
    var volume_id_list: ArrayList<String>? = null
) : BaseObject<T>()

enum class ACGType(val type: String) {
    NOVEL("novel"),
    MANGA("manga"),
    ANIME("anime")
}