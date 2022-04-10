package com.jarvis.acg.api.util

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
                        formMapping[key] = partData
                    }
                    is PartData.BinaryItem -> {}
                }
            }
        }
        return formMapping
    }

    suspend fun requestFileHandling(formMapping: Map<String, Any?>, fileNameList: List<String>) {
        formMapping.forEach { key, value ->
            fileNameList.find { it.contains(key) }?.takeIf { value is PartData.FileItem }?.let {
                // do mapping
                val partData = formMapping[key] as PartData.FileItem
//                partData.forEac
            }
        }
    }
}