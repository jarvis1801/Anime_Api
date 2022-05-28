package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Image
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*

object ImageRoute : BaseEntryRoute<Image>() {
    override var modelEntry: MongoCollection<Image> = KMongoClient.imageEntry
    override var entryPathSection: String = "image"
    override var isEnableDefaultCreate: Boolean = false
    override var isEnableDefaultUpdate: Boolean = false

    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): Image { return Image() }

    fun createImage() {

    }

    suspend fun createThumbnail(image: com.jarvis.acg.api.model.file.Image, chapterId: String? = null): String {
        val entry = Image().apply {
            imageWidth = image.imageWidth
            imageHeight = image.imageHeight
            fileSize = image.fileSize
            url = image.getCallUrl()
            order = image.index
            chapter_id = chapterId
        }
        val result = modelEntry.insertOne(entry)
        return if (result.wasAcknowledged()) {
            image.saveFile()
            entry._id
        } else {
            ""
        }
    }
}