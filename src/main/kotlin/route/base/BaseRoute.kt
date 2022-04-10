package com.jarvis.acg.api.route.base

import io.ktor.routing.*

abstract class BaseRoute {
    abstract fun createRoute(routing: Routing)

}