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

object NovelRoute : BaseEntryRoute<Novel, NovelResponse>() {
    override var modelEntry: MongoCollection<Novel> = KMongoClient.novelEntry
    override var entryPathSection: String = "novel"
    override var translationList: List<String>? = arrayListOf("Extra_name")

    override suspend fun getResponseSpecialHandling(obj: Novel): NovelResponse {
        val response = NovelResponse(obj)
        response.library_list = LibraryRoute.getEntryListByIdList(obj.library_id_list)
        response.painter_list = PainterRoute.getEntryListByIdList(obj.painter_id_list)
        response.publishing_house_list = PublishingHouseRoute.getEntryListByIdList(obj.publishing_house_id_list)
        response.author_list = AuthorRoute.getEntryListByIdList(obj.author_id_list)
        response.work = WorkRoute.getEntryObjectResponseById(obj.work_id)
        return response
    }

    override fun initExtraRoute(route: Route) = route {
        get("list/tag/{id}") {
            getByTagIdList(call)
        }
    }

    private suspend fun getByTagIdList(call: ApplicationCall) {
        call.parameters["id"]?.let { id ->
            val idList = id.split("_")
            WorkRoute.getEntryObjectListByStatement(Work::tag_id_list `in` idList).map { it.id ?: "" }.toList()
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