package com.jarvis.anime.api.route.core

import com.jarvis.anime.api.kmongo.KMongoClient
import com.jarvis.anime.api.kmongo.model.core.PublishingHouse
import com.jarvis.anime.api.model.response.PublishingHouseResponse
import com.jarvis.anime.api.route.base.BaseEntryRoute
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