package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Library
import com.jarvis.acg.api.model.response.LibraryResponse
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object LibraryRoute : BaseEntryRoute<Library, LibraryResponse>() {
    override var modelEntry: MongoCollection<Library> = KMongoClient.libraryEntry
    override var entryPathSection: String = "library"
    override var translationList: List<String>? = arrayListOf("Name")

    override suspend fun getResponseSpecialHandling(obj: Library): LibraryResponse {
        return LibraryResponse(obj)
    }

    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): Library { return Library() }
}