package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Novel
import com.jarvis.acg.api.kmongo.model.core.Work
import com.jarvis.acg.api.model.response.NovelResponse
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.application.*
import io.ktor.routing.*
import org.litote.kmongo.`in`
import org.litote.kmongo.updateOneById

object NovelRoute : BaseEntryRoute<Novel>() {
    override var modelEntry: MongoCollection<Novel> = KMongoClient.novelEntry
    override var entryPathSection: String = "novel"
    override var translationList: List<String>? = arrayListOf("Extra_name")

    override fun initExtraRoute(route: Route) = route {
        get("list/tag/{id}") {
            getByTagIdList(call)
        }
    }

    private suspend fun getByTagIdList(call: ApplicationCall) {
        call.parameters["id"]?.let { id ->
            val idList = id.split("_")
            WorkRoute.getEntryObjectListByStatement(Work::tag_id_list `in` idList).map { it._id }.toList()
                .let { list -> getResponseListByStatement(Novel::work_id `in` list, call) }
        }
    }

    override fun createNewGenericObject(): Novel { return Novel() }

    fun updateVolumeIdAfterCreateVolume(volumeId: String, novelId: String) : Boolean {
        getEntryById(novelId)?.let { novel ->
            novel.volume_id_list?.add(volumeId) ?: run { novel.volume_id_list = arrayListOf(volumeId) }
            modelEntry.updateOneById(novel._id, novel).let {
                return it.modifiedCount == 1L
            }
        } ?: return false
    }
}