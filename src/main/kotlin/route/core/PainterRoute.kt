package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Painter
import com.jarvis.acg.api.model.response.PainterResponse
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object PainterRoute : BaseEntryRoute<Painter>() {
    override var modelEntry: MongoCollection<Painter> = KMongoClient.painterEntry
    override var entryPathSection: String = "painter"
    override var translationList: List<String>? = arrayListOf("Name", "Info")

    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): Painter { return Painter() }
}