package com.jarvis.anime.api.kmongo

import com.jarvis.anime.api.kmongo.model.core.Chapter
import com.jarvis.anime.api.kmongo.model.core.*
import com.jarvis.anime.api.util.EnvironmentUtil
import org.litote.kmongo.KMongo
import org.litote.kmongo.getCollection

object KMongoClient {
    private val client = KMongo.createClient()
    private val database = client.getDatabase(EnvironmentUtil().getMongoDBName())

    val authorEntry = database.getCollection<Author>()
    val workEntry = database.getCollection<Work>()
    val tagEntry = database.getCollection<Tag>()

    val painterEntry = database.getCollection<Painter>()
    val publishingHouseEntry = database.getCollection<PublishingHouse>()
    val libraryEntry = database.getCollection<Library>()

    val novelEntry = database.getCollection<Novel>()
    val mangaEntry = database.getCollection<Manga>()

    val volumeEntry = database.getCollection<Volume>()
    val chapterEntry = database.getCollection<Chapter>()
    val mangaChapterEntry = database.getCollection<MangaChapter>()

    val imageEntry = database.getCollection<Image>()
}