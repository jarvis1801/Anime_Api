package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Author
import com.jarvis.acg.api.model.response.AuthorResponse
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object AuthorRoute : BaseEntryRoute<Author, AuthorResponse>() {

    override var modelEntry: MongoCollection<Author> = KMongoClient.authorEntry
    override var entryPathSection: String = "author"
    override var translationList: List<String>? = arrayListOf("Name", "Info")

    override fun initExtraRoute(route: Route) { }

    override fun createNewGenericObject(): Author { return Author::class.java.newInstance() }

    override suspend fun getResponseSpecialHandling(obj: Author): AuthorResponse {
        return AuthorResponse(obj)
    }
}