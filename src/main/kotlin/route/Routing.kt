package com.jarvis.anime.api.route

import com.jarvis.anime.api.route.core.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*


fun Application.configureRouting() {

    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        routeClasses.forEach {
            it.createRoute(this)
        }
    }
}

private val routeClasses = listOf(
    AuthorRoute,
    WorkRoute,


    TagRoute,


    PainterRoute,
    PublishingHouseRoute,
    LibraryRoute,


    NovelRoute,
    MangaRoute,
    VolumeRoute,
    ChapterRoute,
    MangaChapterRoute,


    WebScrappingRoute(),
    MangaImportRoute(),
    EncryptedImageRoute,


    ImageRoute,
)