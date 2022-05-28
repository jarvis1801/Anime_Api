package com.jarvis.anime.api.route

import com.jarvis.anime.api.route.base.BaseRoute
import com.jarvis.anime.api.util.CipherUtil
import com.jarvis.anime.api.util.ExtensionUtil.decodeUTF8
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths
import kotlin.io.path.absolutePathString

object EncryptedImageRoute : BaseRoute() {

//    const val PATH_MANGA_RESOURCE_ROOT = "D:\\Manga\\"
    const val PATH_MANGA_RESOURCE_ROOT = "C:\\Users\\Jarvis\\Desktop\\test\\"
    val PATH_THUMBNAIL_RESOURCE_ROOT = Paths.get("").absolutePathString() + "\\"

    override fun createRoute(routing: Routing) = routing {
        get("imageResource") {
            getImageResponse(call, PATH_MANGA_RESOURCE_ROOT)
        }

        get("workThumbnail") {
            getImageResponse(call, PATH_THUMBNAIL_RESOURCE_ROOT)
        }
    }

    suspend fun getImageResponse(call: ApplicationCall, prefixPath: String) {
        call.request.queryParameters["path"]?.let { filePath ->
            filePath.decodeUTF8()?.let { decodeFilePath ->
                val file = File("${prefixPath}$decodeFilePath")
                if (file.exists()) {
                    val inputStream = FileInputStream(file)
                    val inputBytes = inputStream.readBytes()

                    val encodeString = CipherUtil.encode(inputBytes)

                    call.respondText { encodeString ?: "" }
//                        call.respondFile(file)
                } else {
                    call.respondText("Error", status = HttpStatusCode.NotFound)
                }
            }
        }
    }
}