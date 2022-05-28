package com.jarvis.anime.api.route.core

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.core.Library
import com.jarvis.anime.api.model.response.LibraryResponse
import com.jarvis.anime.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object LibraryRoute : BaseEntryRoute<Library>() {
    override var modelEntry: MongoCollection<Library> = KMongoClient.libraryEntry
    override var entryPathSection: String = "library"
    override var translationList: List<String>? = arrayListOf("Name")


    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): Library { return Library() }
}