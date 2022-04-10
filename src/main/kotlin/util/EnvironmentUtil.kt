package com.jarvis.acg.api.util

import com.jarvis.acg.api.App
import com.jarvis.acg.api.env

class EnvironmentUtil {
    companion object {
        const val ENV_PROD = "prod"
        const val ENV_UAT = "uat"
        const val ENV_DEV = "dev"
    }

    fun getMongoDBName() : String {
        return when (App.application?.env) {
            ENV_PROD -> "acg"
            ENV_UAT -> "acg_uat"
            ENV_DEV -> "acg_dev"
            else -> "acg"
        }
    }
}