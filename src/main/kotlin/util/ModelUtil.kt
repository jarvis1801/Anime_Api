package com.jarvis.acg.api.util

import com.jarvis.acg.api.model.file.BaseFile
import com.jarvis.acg.api.model.file.Image
import com.jarvis.acg.api.util.ExtensionUtil.replaceBreakLine
import com.jarvis.acg.api.util.ExtensionUtil.replaceWhiteSpace
import io.ktor.http.content.*

class ModelUtil {

    suspend fun convertFormMapping(multiPartData: MultiPartData, trimList: List<String>?) : HashMap<String, Any?> {
        val formMapping = hashMapOf<String, Any?>()
        multiPartData.forEachPart { partData ->
            partData.name?.let { key ->
                when (partData) {
                    is PartData.FormItem -> {
                        // return string
                        var value = partData.value
                        trimList?.find { it.contains(key, true) }.run { value = value.replaceWhiteSpace().replaceBreakLine() }
                        formMapping[key] = value
                    }
                    is PartData.FileItem -> {
                        // return fileItem
                        val fileMap = formMapping[key] as ArrayList<PartData.FileItem>?
                        if (fileMap.isNullOrEmpty()) {
                            formMapping[key] = arrayListOf<PartData.FileItem>().apply { add(partData) }
                        } else {
                            (formMapping[key] as ArrayList<PartData.FileItem>).add(partData)
                        }
                    }
                    is PartData.BinaryItem -> {}
                }
            }
        }
        return formMapping
    }

    suspend fun requestFileHandling(formMapping: Map<String, Any?>, fileNameList: List<String>): ArrayList<BaseFile> {
        val arrayList = arrayListOf<BaseFile>()
        formMapping.forEach { key, value ->
            fileNameList.find { it.contains(key) }?.takeIf { value is ArrayList<*> }?.let {
                // do mapping
                val partDataList = formMapping[key] as ArrayList<PartData.FileItem>
                var fileIndex = 1
                partDataList.forEach {
                    val image = Image().apply {
                        partData = it
                        index = fileIndex++
                    }
                    if (image is Image) {
                        image.initMetaInfo()
                    }
                    arrayList.add(image)
                }
            }
        }
        return arrayList
    }
}