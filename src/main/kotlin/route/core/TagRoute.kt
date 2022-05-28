package com.jarvis.anime.api.route.core

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.core.Tag
import com.jarvis.anime.api.model.response.TagResponse
import com.jarvis.anime.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object TagRoute : BaseEntryRoute<Tag>() {
    override var modelEntry: MongoCollection<Tag> = KMongoClient.tagEntry
    override var entryPathSection: String = "tag"
    override var translationList: List<String>? = arrayListOf("Name")

    override fun initExtraRoute(route: Route) { }

    override fun createNewGenericObject(): Tag {
        return Tag()
    }
}