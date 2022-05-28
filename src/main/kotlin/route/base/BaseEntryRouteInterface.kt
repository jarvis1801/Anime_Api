package com.jarvis.anime.api.route.base

import com.jarvis.anime.api.kmongo.model.base.BaseObject
import io.ktor.routing.*

interface BaseEntryRouteInterface<E: BaseObject<E>> {
    fun initExtraRoute(route: Route)
    fun createNewGenericObject() : E
}