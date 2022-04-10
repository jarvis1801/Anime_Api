package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Work
import com.jarvis.acg.api.model.response.WorkResponse
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object WorkRoute : BaseEntryRoute<Work, WorkResponse>() {

    override var modelEntry: MongoCollection<Work> = KMongoClient.workEntry
    override var entryPathSection: String = "work"
    override var translationList: List<String>? = arrayListOf("Name")

    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): Work { return Work() }

    override suspend fun getResponseSpecialHandling(obj: Work): WorkResponse {
        val response = WorkResponse(obj)
        response.tag_list = TagRoute.getEntryListByIdList(obj.tag_id_list)
        return response
    }
}