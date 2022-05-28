package com.jarvis.anime.api.route.core

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.core.Chapter
import com.jarvis.anime.api.kmongo.model.core.Volume
import com.jarvis.anime.api.model.response.ChapterResponse
import com.jarvis.anime.api.route.base.BaseEntryRoute
import com.jarvis.anime.api.util.ExtensionUtil.getResponse
import com.mongodb.client.MongoCollection
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.*

object ChapterRoute : BaseEntryRoute<Chapter>() {
    override var modelEntry: MongoCollection<Chapter> = KMongoClient.chapterEntry
    override var entryPathSection: String = "chapter"
    override var translationList: List<String>? = arrayListOf("Name", "SectionName", "Content")
    override var trimList: List<String>? = arrayListOf("Content")

    override var isEnableDefaultDeleteById: Boolean = false

    override fun initExtraRoute(route: Route) {
        deleteById(route)
    }

    private fun deleteById(route: Route) = route {
        delete("{id}") {
            call.parameters["id"]?.let { id ->
                getEntryById(id)?.let {
                    val volumeId = it.volume_id
                    val statement = arrayOf(Volume::chapter_id_list `in` id, Volume::_id eq volumeId)
                    if (VolumeRoute.deleteVolumeIdAfterDeleteChapter(id, statement)) {
                        modelEntry.deleteOneById(id).let { result ->
                            if (result.deletedCount > 0) {
                                return@delete call.respondText { it.getResponse() }
                            }
                        }
                    }
                }
            }
            call.respondText { "Error" }
        }
    }

    override fun createNewGenericObject(): Chapter { return Chapter() }

    override fun createdHandling(obj: Chapter, requestFormMapping: HashMap<String, Any?>): Chapter? {
        val volumeId = requestFormMapping["volume_id"]
        if (volumeId !is String) return null
        return VolumeRoute.updateVolumeIdAfterCreateChapter(obj._id, volumeId).let { if (it) obj else null }
    }
}