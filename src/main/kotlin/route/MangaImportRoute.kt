package com.jarvis.anime.api.route

import com.jarvis.anime.api.model.file.Image
import com.jarvis.anime.api.route.base.BaseRoute
import com.jarvis.anime.api.route.core.ImageRoute
import com.jarvis.anime.api.util.AutoImportUtil
import com.jarvis.anime.api.util.ExtensionUtil.returnFileList
import com.jarvis.anime.api.util.ExtensionUtil.returnFolderList
import io.ktor.routing.*
import java.io.File

class MangaImportRoute : BaseRoute() {
    companion object {
        const val PATH_MANGA_PATH = "D:\\Manga\\"
    }
    override fun createRoute(routing: Routing) = routing {
        post("mangaImport") {

            val mangaDirectory = File(PATH_MANGA_PATH)
            val mangaList = mangaDirectory.returnFolderList()
            mangaList?.forEach { mangaFolder ->
                println(mangaFolder.name)

                if (mangaFolder.name == "科學超電磁砲") {

                    val work = AutoImportUtil.getOrCreateWork(mangaFolder.name)
                    val manga = AutoImportUtil.getOrCreateManga(work._id)

                    val mangaVolumeList = mangaFolder.returnFolderList()
                    mangaVolumeList?.forEach { volumeFolder ->

                        val volume = AutoImportUtil.getOrCreateVolume(manga._id, volumeFolder.name)

                        val mangaChapterFolderList = volumeFolder.returnFolderList()
                        mangaChapterFolderList?.forEach { mangaChapterFolder ->

                            var fileIndex = 1
                            val imageList = mangaChapterFolder.returnFileList()?.sortedWith(
                                compareBy { it.name.length }
                            )?.map {
                                Image().apply {
                                    file = it
                                    index = fileIndex++
                                    initMetaInfo()
                                }
                            }
                            val prefixPath = "${work.name?.tc}\\${volume.name?.tc}\\${mangaChapterFolder.name}"

                            val chapter = AutoImportUtil.createChapter(volume._id, mangaChapterFolder.name)

                            /**
                             * chapter == null -> currently has the entry,
                             * chapter != null -> create entry
                             */
                            if (chapter != null) {
                                val mediaIdList = imageList?.map { image ->
                                    image.isCreateNewFile = false
                                    image.savePathPrefix = prefixPath
                                    ImageRoute.createThumbnail(image, chapter._id)
                                }?.toCollection(ArrayList())

                                AutoImportUtil.insertChapter(chapter, volume._id, mediaIdList)
                            }
                        }
                    }
                }
            }
        }
    }
}