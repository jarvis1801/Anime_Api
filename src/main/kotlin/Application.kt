package com.jarvis.anime.api

import com.jarvis.anime.api.App.initApp
import com.jarvis.anime.api.route.configureRouting
import io.ktor.application.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    initApp(this)
}

object App {
    var application : Application? = null

    fun initApp(application: Application) {
        this.application = application
        application.configureRouting()
    }
}

val Application.env get() = environment.config.propertyOrNull("ktor.environment")?.getString()