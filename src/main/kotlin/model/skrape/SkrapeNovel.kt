package com.jarvis.anime.api.model.skrape

class SkrapeNovel(
    var title: String? = "",
    var volumeList: ArrayList<SkrapeNovelVolume>? = arrayListOf()
)

class SkrapeNovelVolume(
    var volumeName: String,
    var chapterList: ArrayList<SkrapeNovelChapter>? = arrayListOf(),
    var order: Int = -1
)

class SkrapeNovelChapter(
    var chapterName: String,
    var chapterUrl: String,
    var sectionName: String = "",
    var chapterContent: String = "",
    var order: Int = -1
)