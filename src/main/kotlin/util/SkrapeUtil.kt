package com.jarvis.acg.api.util

import com.jarvis.acg.api.model.skrape.SkrapeNovel
import com.jarvis.acg.api.model.skrape.SkrapeNovelChapter
import com.jarvis.acg.api.model.skrape.SkrapeNovelVolume
import com.jarvis.acg.api.util.ExtensionUtil.replaceBreakLine
import com.jarvis.acg.api.util.ExtensionUtil.replaceWhiteSpace
import it.skrape.core.htmlDocument
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.Doc
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

class SkrapeUtil {

    suspend fun fetchNovel(url: String, titleNotContainList: List<String>? = null) : SkrapeNovel {
        val skrapeDoc = fetch(url)

        val skrapeObject = SkrapeNovel()
        val elementList = getNovelVolumeElementList(skrapeDoc, titleNotContainList)
        val title = getNovelTitle(skrapeDoc)
        skrapeObject.title = title


        return getNovelVolumeSkrapeObjectList(skrapeObject, elementList)
    }

    private fun getNovelVolumeSkrapeObjectList(skrapeObject: SkrapeNovel, elementList: ArrayList<Elements>): SkrapeNovel {
        elementList.forEachIndexed { index, item ->
            val textList = item.text().split(" ")
            var volumeName = ""
            var name = ""
            var sectionName = ""
            when (textList.size) {
                2 -> {
                    name = textList[1]
                    volumeName = textList[0]
                }
                in 3..999 -> {
                    textList.forEachIndexed{ textIndex, title ->
                        if (textIndex >= 2) name += " $title"
                    }
                    sectionName = textList[1]
                    volumeName = textList[0]
                }
            }
            val volume: SkrapeNovelVolume = skrapeObject.volumeList?.find { it.volumeName == volumeName } ?: run {
                val volume = SkrapeNovelVolume(volumeName)
                skrapeObject.volumeList?.add(volume)
                volume
            }
            val chapter = SkrapeNovelChapter(
                name.trim(),
                item.attr("href"),
                sectionName.trim(),
                "",
                index
            )

            volume.chapterList?.add(chapter)
        }
        return skrapeObject
    }

    suspend fun fetchChapter(chapter: SkrapeNovelChapter, url: String): SkrapeNovelChapter {
        val skrapeDoc = fetch(url)

        val chapterElement = getNovelChapterElement(skrapeDoc)
        chapter.chapterContent = chapterElement.text().replaceBreakLine().replaceWhiteSpace("\n")
        return chapter
    }

    private fun getNovelTitle(skrapeDoc: Doc): String? {
        return skrapeDoc.document.getElementsByTag("meta").find { it.attr("name") == "name" }?.attr("content")

    }

    private fun getNovelChapterElement(skrapeDoc: Doc): Element {
        return skrapeDoc.document.getElementById("content").getElementById("text")
    }

    private suspend fun fetch(url: String) : Doc {
        return skrape(BrowserFetcher) {
            request { this.url = url }
        }.response { htmlDocument { this } }
    }

    private fun getNovelVolumeElementList(skrapeDoc: Doc, titleNotContainList: List<String>?): ArrayList<Elements> {

        val elementList = skrapeDoc.document.allElements.filter { it.classNames().contains("episode_li") }.map {
            it.getElementsByTag("span").remove()
            it.getElementsByTag("a")
        }.toCollection(ArrayList())

        if (titleNotContainList != null) {
            val removeList = arrayListOf<Elements>()
            elementList.forEach { element ->
                titleNotContainList.forEach {
                    if (element != null && element.text().contains(it) && elementList.contains(element)) {
                        removeList.add(element)
                    }
                }
            }
            elementList.removeAll(removeList.toSet())
        }
        return elementList
    }

    fun getUrlPrefix(url: String): String {
        var prefix = ""
        if (url.startsWith("https://8book.com")) {
            prefix = "https://8book.com"
        }
        return prefix
    }


}