package com.jarvis.acg.api.util

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.base.Translation
import com.jarvis.acg.api.kmongo.model.core.*
import com.jarvis.acg.api.route.core.*
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

    suspend fun getOrCreateChapter(volumeId: String, title: String, mediaIdList: ArrayList<String>?): MangaChapter {
        val statement = arrayOf(MangaChapter::sectionName / Translation::tc eq title)
        return MangaChapterRoute.getEntryByStatement(statement) ?: run {
            val insertMangaChapter = MangaChapter().apply {
                sectionName = Translation(tc = title)
                image_id_list = mediaIdList
            }
            KMongoClient.mangaChapterEntry.insertOne(insertMangaChapter)
            VolumeRoute.updateVolumeIdAfterCreateChapter(insertMangaChapter._id, volumeId)
            insertMangaChapter
        }
    }
}