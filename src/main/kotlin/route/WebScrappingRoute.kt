package com.jarvis.acg.api.route

import com.jarvis.acg.api.kmongo.KMongoClient
import com.jarvis.acg.api.kmongo.model.base.Translation
import com.jarvis.acg.api.kmongo.model.core.*
import com.jarvis.acg.api.route.base.BaseRoute
import com.jarvis.acg.api.route.core.*
import com.jarvis.acg.api.util.ModelUtil
import com.jarvis.acg.api.util.SkrapeUtil
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.litote.kmongo.`in`
import org.litote.kmongo.div
import org.litote.kmongo.eq

class WebScrappingRoute : BaseRoute() {



    override fun createRoute(routing: Routing) {
        routing {
            post("webScrapping/novel") {
                val requestFormMapping = ModelUtil().convertFormMapping(call.receiveMultipart(), arrayListOf())
                val url = requestFormMapping["url"] as String
                val urlPrefix = SkrapeUtil().getUrlPrefix(url)
                val titleNotContainList = (requestFormMapping["title_not_contain_list"] as String).takeIf { it.isNotBlank() }?.split(",") ?: run { null }
                val skrapeNovel = SkrapeUtil().fetchNovel(url, titleNotContainList)

                skrapeNovel.takeIf { !it.author.isNullOrBlank() && !it.title.isNullOrBlank() }?.let {
                    val workStatement = arrayOf(Work::name / Translation::tc eq skrapeNovel.title)
                    val work = WorkRoute.getEntryByStatement(workStatement) ?: run {
                        val insertWork = Work().apply {
                            name = Translation(tc = skrapeNovel.title)
                        }
                        KMongoClient.workEntry.insertOne(insertWork)
                        insertWork
                    }

                    val author = AuthorRoute.getEntryByStatement(Author::name / Translation::tc eq skrapeNovel.author) ?: run {
                        val insertAuthor = Author().apply {
                            name = Translation(tc = skrapeNovel.author)
                        }
                        KMongoClient.authorEntry.insertOne(insertAuthor)
                        insertAuthor
                    }

                    val novelStatement = arrayOf(Novel::work_id eq work._id, Novel::author_id_list `in` author._id)
                    val novel = NovelRoute.getEntryByStatement(novelStatement) ?: run {
                        val insertNovel = Novel().apply {
                            author_id_list = arrayListOf(author._id)
                            work_id = work._id
                        }
                        KMongoClient.novelEntry.insertOne(insertNovel)
                        insertNovel
                    }

                    val volumeList = VolumeRoute.getEntryListByIdList(novel.volume_id_list) ?: arrayListOf()
                    val volumeTitleList = volumeList.map { it.name?.tc }

                    skrapeNovel.volumeList?.forEach {
                        val volume = if (volumeTitleList.contains(it.volumeName)) {
                            val volumeStatement = arrayOf(Volume::book_id eq novel._id, Volume::name / Translation::tc eq it.volumeName)
                            VolumeRoute.getEntryByStatement(volumeStatement)
                        } else {
                            val insertVolume = Volume().apply {
                                name = Translation(tc = it.volumeName)
                            }

                            KMongoClient.volumeEntry.insertOne(insertVolume)
                            NovelRoute.updateVolumeIdAfterCreateVolume(insertVolume._id, novel._id)
                            insertVolume
                        }

                        it.chapterList?.forEach { skrapeChapter ->
                            val chapterDoc = SkrapeUtil().fetchChapter(skrapeChapter, "${urlPrefix}${skrapeChapter.chapterUrl}")

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
                    call.respondText { "Success" }
                } ?: run {
                    call.respondText { "Missing Novel" }
                }
            }
        }
    }
}