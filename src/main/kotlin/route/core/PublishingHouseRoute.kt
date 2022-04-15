package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.PublishingHouse
import com.jarvis.acg.api.model.response.PublishingHouseResponse
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object PublishingHouseRoute : BaseEntryRoute<PublishingHouse>() {
    override var modelEntry: MongoCollection<PublishingHouse> = KMongoClient.publishingHouseEntry
    override var entryPathSection: String = "publishingHouse"
    override var translationList: List<String>? = arrayListOf("Name")

    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): PublishingHouse {
        return PublishingHouse()
    }
}