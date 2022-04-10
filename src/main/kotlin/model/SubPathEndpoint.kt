package com.jarvis.acg.api.model

import io.ktor.http.cio.*
import org.bson.conversions.Bson

data class SubPathEndpoint(
    val subPath: String,
    val statement: Bson,
    val requestMethod: String
)
