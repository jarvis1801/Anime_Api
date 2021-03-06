package com.jarvis.anime.api.route.core

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.base.AnimeType
import com.jarvis.anime.api.kmongo.model.core.Volume
import com.jarvis.anime.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*
import org.bson.conversions.Bson
import org.litote.kmongo.updateOneById

object VolumeRoute : BaseEntryRoute<Volume>() {
    override var modelEntry: MongoCollection<Volume> = KMongoClient.volumeEntry
    override var entryPathSection: String = "volume"
    override var translationList: List<String>? = arrayListOf("Name", "Sticky_header")

    override fun initExtraRoute(route: Route) { }

    override fun createNewGenericObject(): Volume { return Volume() }

    override fun createdHandling(obj: Volume, requestFormMapping: HashMap<String, Any?>): Volume? {
        val type = requestFormMapping["anime_type"]
        val bookId = requestFormMapping["book_id"]
        if (bookId !is String) return null
        when (type) {
            AnimeType.NOVEL.type.lowercase() -> NovelRoute.updateVolumeIdAfterCreateVolume(obj._id, bookId).let { if (it) return obj }
            AnimeType.MANGA.type.lowercase() -> MangaRoute.updateVolumeIdAfterCreateVolume(obj._id, bookId).let { if (it) return obj }
        }
        return null
    }

    fun updateVolumeIdAfterCreateChapter(chapterId: String, volumeId: String) : Boolean {
        getEntryById(volumeId)?.let { volume ->
            volume.chapter_id_list?.add(chapterId) ?: run { volume.chapter_id_list = arrayListOf(chapterId) }
            modelEntry.updateOneById(volume._id, volume).let {
                return it.modifiedCount == 1L
            }
        } ?: return false
    }

    suspend fun deleteVolumeIdAfterDeleteChapter(chapterId: String, statement: Array<Bson>) : Boolean {
        getEntryByStatement(statement)?.let { volume ->
            volume.chapter_id_list?.remove(chapterId) ?: run { volume.chapter_id_list = arrayListOf(chapterId) }
            modelEntry.updateOneById(volume._id, volume).let {
                return it.modifiedCount == 1L
            }
        } ?: return false
    }
}