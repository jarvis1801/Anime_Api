package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Work
import com.jarvis.acg.api.model.file.BaseFile
import com.jarvis.acg.api.model.file.Image
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object WorkRoute : BaseEntryRoute<Work>() {

    override var modelEntry: MongoCollection<Work> = KMongoClient.workEntry
    override var entryPathSection: String = "work"
    override var translationList: List<String>? = arrayListOf("Name")
    override var fileNameList: List<String>? = arrayListOf("thumbnail")

    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): Work { return Work() }

    override suspend fun createdHandlingFile(obj: Work, baseFileList: ArrayList<BaseFile>?): Work {
        val imageList = baseFileList?.filterIsInstance<Image>()

        val mediaIdList = imageList?.map { image ->
            image.savePathPrefix = "${obj.name?.tc}"
//            ImageRoute.createThumbnail(image, chapter._id)
            ImageRoute.createThumbnail(image)
        }

        obj.thumbnail_id_list = mediaIdList?.toCollection(ArrayList())
        return obj
    }
}