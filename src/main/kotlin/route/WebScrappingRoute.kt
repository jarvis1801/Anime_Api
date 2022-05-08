package com.jarvis.acg.api.route

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.base.Translation
import com.jarvis.acg.api.kmongo.model.core.Chapter
import com.jarvis.acg.api.kmongo.model.core.Novel
import com.jarvis.acg.api.kmongo.model.core.Volume
import com.jarvis.acg.api.kmongo.model.core.Work
import com.jarvis.acg.api.route.base.BaseRoute
import com.jarvis.acg.api.route.core.ChapterRoute
import com.jarvis.acg.api.route.core.NovelRoute
import com.jarvis.acg.api.route.core.VolumeRoute
import com.jarvis.acg.api.route.core.WorkRoute
import com.jarvis.acg.api.util.ModelUtil
import com.jarvis.acg.api.util.SkrapeUtil
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.div
import org.litote.kmongo.eq

class WebScrappingRoute : BaseRoute() {



    override fun createRoute(routing: Routing) {
        routing {
            post("webScrapping/novel") {
                val requestFormMapping = ModelUtil().convertFormMapping(call.receiveMultipart(), arrayListOf())
                val url = requestFormMapping["url"] as String
                val urlPrefix = SkrapeUtil().getUrlPrefix(url)
                val titleNotContainList = (requestFormMapping["title_not_contain_list"] as String).split(",")
                val skrapeNovel = SkrapeUtil().fetchNovel(url, titleNotContainList)

                // TODO
                // get existing or need to create work, author, novel by name
                val work = WorkRoute.getEntryByStatement(Work::name / Translation::tc eq skrapeNovel.title)
                if (work != null) {
                    val novel = NovelRoute.getEntryByStatement(Novel::work_id eq work._id)

                    if (novel != null) {
                        val volumeList = VolumeRoute.getEntryListByIdList(novel.volume_id_list) ?: arrayListOf()
                        val volumeTitleList = volumeList.map { it.name?.tc }

                        println(volumeList.size)
                        println(volumeTitleList.size)
                        skrapeNovel.volumeList?.forEach {
                            println("                        skrapeNovel.volumeList?.forEach {")
                            val volume = if (volumeTitleList.contains(it.volumeName)) {
                                val volumeStatement = arrayOf(Volume::book_id eq novel._id, Volume::name / Translation::tc eq it.volumeName)
                                VolumeRoute.getEntryByStatement(volumeStatement)
                            } else {
                                val insertVolume = Volume().apply {
                                    name = Translation(tc = it.volumeName)
                                    book_id = novel._id
                                }

                                KMongoClient.volumeEntry.insertOne(insertVolume)
                                NovelRoute.updateVolumeIdAfterCreateVolume(insertVolume._id, novel._id)
                                insertVolume
                            }

                            it.chapterList?.forEach { skrapeChapter ->
                                println("                            it.chapterList?.forEach { skrapeChapter ->")
                                println("skrapeChapter" + skrapeChapter.chapterName)
                                println("volume!!._id" + volume!!._id)
                                val chapterDoc = SkrapeUtil().fetchChapter(skrapeChapter, "${urlPrefix}${skrapeChapter.chapterUrl}")

                                println("chapterDoc.chapterName" + chapterDoc.chapterName)

                                val chapterStatement = arrayOf(Chapter::volume_id eq volume!!._id, Chapter::name / Translation::tc eq chapterDoc.chapterName)
                                ChapterRoute.getEntryByStatement(chapterStatement)?.let {} ?: run {
                                    val chapter = Chapter().apply {
                                        sectionName = Translation(tc = chapterDoc.sectionName)
                                        name = Translation(tc = chapterDoc.chapterName)
                                        content = Translation(tc = chapterDoc.chapterContent)
                                        volume_id = volume._id
                                    }

                                    KMongoClient.chapterEntry.insertOne(chapter)
                                    VolumeRoute.updateVolumeIdAfterCreateChapter(chapter._id, volume._id)
                                }
                            }
                        }

                    } else {
                        call.respondText { "Novel Null ${skrapeNovel.title} ${work._id}" }
                    }
                } else {
                    call.respondText { "Null ${skrapeNovel.title}" }
                }
            }
        }
    }
}