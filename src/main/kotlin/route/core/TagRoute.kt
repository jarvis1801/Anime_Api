package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Tag
import com.jarvis.acg.api.model.response.TagResponse
import com.jarvis.acg.api.route.base.BaseEntryRoute
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