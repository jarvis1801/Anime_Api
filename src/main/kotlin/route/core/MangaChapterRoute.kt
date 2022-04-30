package com.jarvis.acg.api.route.core

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.core.Manga
import com.jarvis.acg.api.kmongo.model.core.MangaChapter
import com.jarvis.acg.api.model.file.BaseFile
import com.jarvis.acg.api.model.file.Image
import com.jarvis.acg.api.route.base.BaseEntryRoute
import com.mongodb.client.MongoCollection
import io.ktor.routing.*
import org.litote.kmongo.contains

object MangaChapterRoute: BaseEntryRoute<MangaChapter>() {
    override var modelEntry: MongoCollection<MangaChapter> = KMongoClient.mangaChapterEntry
    override var entryPathSection: String = "mangaChapter"
    override var translationList: List<String>? = arrayListOf("Name", "SectionName")
    override var fileNameList: List<String>? = arrayListOf("imageList")

    override fun initExtraRoute(route: Route) {}

    override fun createNewGenericObject(): MangaChapter { return MangaChapter() }

    override suspend fun createdHandlingFile(obj: MangaChapter, baseFileList: ArrayList<BaseFile>?): MangaChapter {
        val imageList = baseFileList?.filterIsInstance<Image>()

        val prefixPath = getMangaImagePrefixPath(obj)

        val mediaIdList = imageList?.map { image ->
            image.isCreateNewFile = false
            image.savePathPrefix = prefixPath
            ImageRoute.createThumbnail(image)
        }

        obj.imageList = mediaIdList?.toCollection(ArrayList())
        return obj
    }

    override fun createdHandling(obj: MangaChapter, requestFormMapping: HashMap<String, Any?>): MangaChapter? {
        val volumeId = requestFormMapping["volume_id"]
        if (volumeId !is String) return null
        return VolumeRoute.updateVolumeIdAfterCreateChapter(obj._id, volumeId).let { if (it) obj else null }
    }

    private suspend fun getMangaImagePrefixPath(obj: MangaChapter): String? {
        val volume = VolumeRoute.getEntryById(obj.volume_id!!)
        MangaRoute.getEntryByStatement(Manga::volume_id_list contains volume?._id)?.let { manga ->
            manga.work_id?.let {
                val work = WorkRoute.getEntryById(manga.work_id!!)

                val path = "${work?.name?.tc}\\${volume?.name?.tc}\\${obj.sectionName?.tc}"

                return path
            }
        }

        return null
    }
}