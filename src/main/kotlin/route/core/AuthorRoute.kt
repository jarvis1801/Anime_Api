package com.jarvis.anime.api.route.core

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.core.Author
import com.jarvis.anime.api.model.response.AuthorResponse
import com.jarvis.anime.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object AuthorRoute : BaseEntryRoute<Author>() {

    override var modelEntry: MongoCollection<Author> = KMongoClient.authorEntry
    override var entryPathSection: String = "author"
    override var translationList: List<String>? = arrayListOf("Name", "Info")

    override fun initExtraRoute(route: Route) { }

    override fun createNewGenericObject(): Author { return Author::class.java.newInstance() }
}