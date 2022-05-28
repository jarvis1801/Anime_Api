package com.jarvis.anime.api.model.file

import io.ktor.http.content.*
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.absolutePathString

abstract class BaseFile {

    private val resourcePath: String = Paths.get("resources").absolutePathString()
    open var partData: PartData.FileItem? = null
    open var file: File? = null
    open var index = -1
    open var savePathPrefix: String? = null
    open var isCreateNewFile = true
    open var fileBytes: ByteArray? = null
    open var fileSize: Int? = null

    private fun getModifyFileName() : String? {
        val index = if (index <= 0) {
            ""
        } else {
            String.format("%04d", index)
        }

        return when (this) {
            is Image -> {
                getExtension()?.let {
                    "${index}.${getExtension()}"
                } ?: let {
                    partData?.name
                }
            }
            else -> {
                getExtension()?.let {
                    "${index}.${getExtension()}"
                } ?: let {
                    partData?.name
                }
            }
        }
    }

    fun getFileName(): String? {
        return if (partData != null) {
            partData?.name
        } else if (file != null) {
            file?.name
        } else null
    }

    fun saveFile() {
        partData?.takeIf { isCreateNewFile && fileBytes != null }?.let {
            checkAndCreateDirectory(getSaveDirectory())

            File(getSaveFilePath()).apply { writeBytes(fileBytes!!) }
        }
    }

    private fun getSaveFilePath(): String {
        val fileName = getModifyFileName()
        return "$resourcePath\\$savePathPrefix\\$fileName"
    }

    private fun getSaveDirectory(): String {
        val savePath = getSaveFilePath()
        return savePath.lastIndexOf("\\").run { savePath.substring(0, this) }
    }

    fun getCallUrl(): String {
        return if (isCreateNewFile) {
            val fileName = getModifyFileName()
            "resources\\$savePathPrefix\\$fileName"
        } else {
            val originFileName = getFileName()
            "\\$savePathPrefix\\$originFileName"
        }
    }

    private fun getExtension(): String? { return partData?.originalFileName?.split(".")?.last() }

    private fun checkAndCreateDirectory(path: String) {
        val folder = File(path)
        if (!folder.exists()) {
            folder.mkdirs()
        }
    }
}