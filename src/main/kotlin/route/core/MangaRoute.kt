package com.jarvis.anime.api.route.core

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.core.Manga
import com.jarvis.anime.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*
import org.litote.kmongo.updateOneById

object MangaRoute : BaseEntryRoute<Manga>() {
    override var modelEntry: MongoCollection<Manga> = KMongoClient.mangaEntry
    override var entryPathSection: String = "manga"

    override fun initExtraRoute(route: Route) { }

    override fun createNewGenericObject(): Manga { return Manga() }

    fun updateVolumeIdAfterCreateVolume(volumeId: String, mangaId: String) : Boolean {
        getEntryById(mangaId)?.let { manga ->
            manga.volume_id_list?.add(volumeId) ?: run { manga.volume_id_list = arrayListOf(volumeId) }
            modelEntry.updateOneById(manga._id, manga).let {
                return it.modifiedCount == 1L
            }
        } ?: return false
    }
}