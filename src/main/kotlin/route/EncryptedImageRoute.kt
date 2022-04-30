package com.jarvis.acg.api.route

import com.jarvis.acg.api.route.base.BaseRoute
import com.jarvis.acg.api.util.CipherUtil
import com.jarvis.acg.api.util.ExtensionUtil.decodeUTF8
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.io.FileInputStream

object EncryptedImageRoute : BaseRoute() {

    const val PATH_MANGA_RESOURCE_ROOT = "D:\\Manga\\"

    override fun createRoute(routing: Routing) = routing {
        get("imageResource") {
            call.request.queryParameters["path"]?.let { filePath ->
                filePath.decodeUTF8()?.let { decodeFilePath ->
                    val file = File("${PATH_MANGA_RESOURCE_ROOT}$decodeFilePath")
                    if (file.exists()) {
                        val inputStream = FileInputStream(file)
                        val inputBytes = inputStream.readBytes()

                        val encodeString = CipherUtil.encode(inputBytes)

                        call.respondText { encodeString ?: "" }
                    } else {
                        call.respondText("Error", status = HttpStatusCode.NotFound)
                    }
                }
            }
        }
    }
}