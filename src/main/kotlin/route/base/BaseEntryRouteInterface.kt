package com.jarvis.acg.api.route.base

import com.jarvis.acg.api.kmongo.model.base.BaseObject
import io.ktor.routing.*

interface BaseEntryRouteInterface<E: BaseObject<E>> {
    fun initExtraRoute(route: Route)
    fun createNewGenericObject() : E
}