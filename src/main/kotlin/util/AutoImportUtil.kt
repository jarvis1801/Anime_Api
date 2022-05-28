package com.jarvis.anime.api.util

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.base.Translation
import com.jarvis.anime.api.kmongo.model.core.*
import com.jarvis.anime.api.route.core.*
import org.litote.kmongo.div
import org.litote.kmongo.eq

object AutoImportUtil {

    suspend fun getOrCreateWork(title: String): Work {
        val statement = arrayOf(Work::name / Translation::tc eq title)
        return WorkRoute.getEntryByStatement(statement) ?: run {
            val insertWork = Work().apply {
                name = Translation(tc = title)
            }
            KMongoClient.workEntry.insertOne(insertWork)
            insertWork
        }
    }

    suspend fun getOrCreateManga(workId: String): Manga {
        val statement = arrayOf(Manga::work_id eq workId)
        return MangaRoute.getEntryByStatement(statement) ?: run {
            val insertManga = Manga().apply {
                work_id = workId
            }
            KMongoClient.mangaEntry.insertOne(insertManga)
            insertManga
        }
    }

    suspend fun getOrCreateVolume(bookId: String, title: String): Volume {
        val statement = arrayOf(Volume::book_id eq bookId, Volume::name / Translation::tc eq title)
        return VolumeRoute.getEntryByStatement(statement) ?: run {
            val insertVolume = Volume().apply {
                book_id = bookId
                name = Translation(tc = title)
            }
            KMongoClient.volumeEntry.insertOne(insertVolume)
            MangaRoute.updateVolumeIdAfterCreateVolume(insertVolume._id, bookId)
            insertVolume
        }
    }

    suspend fun createChapter(volumeId: String, title: String): MangaChapter? {
        val statement = arrayOf(MangaChapter::sectionName / Translation::tc eq title)
        val entry = MangaChapterRoute.getEntryByStatement(statement)
        if (entry != null) return null
        return run {
            val insertMangaChapter = MangaChapter().apply {
                sectionName = Translation(tc = title)
                volume_id = volumeId
            }
            insertMangaChapter
        }
    }

    suspend fun insertChapter(mangaChapter: MangaChapter, volumeId: String, mediaIdList: ArrayList<String>?) {
        mangaChapter.apply {
            image_id_list = mediaIdList
            KMongoClient.mangaChapterEntry.insertOne(mangaChapter)
            VolumeRoute.updateVolumeIdAfterCreateChapter(mangaChapter._id, volumeId)
        }
    }
}