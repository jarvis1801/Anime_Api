package com.jarvis.anime.api.util

import com.jarvis.anime.api.App
import com.jarvis.anime.api.env

class EnvironmentUtil {
    companion object {
        const val ENV_PROD = "prod"
        const val ENV_UAT = "uat"
        const val ENV_DEV = "dev"
    }

    fun getMongoDBName() : String {
        return when (App.application?.env) {
            ENV_PROD -> "anime"
            ENV_UAT -> "anime_uat"
            ENV_DEV -> "anime_dev"
            else -> "anime"
        }
    }
}