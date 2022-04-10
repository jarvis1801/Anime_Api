package com.jarvis.acg.api.util

import com.jarvis.acg.api.kmongo.model.base.Translation

class TranslationUtil {

    private val supportLanguage = arrayListOf("tc", "en", "jp", "ro")

    suspend fun convertPropertyToTranslation(requestFormMapping: HashMap<String, Any?>, translationList: List<String>?) : HashMap<String, Any?> {
        val formMapping = requestFormMapping.clone() as HashMap<String, Any?>
        translationList?.forEach { property ->
            val translation = Translation()
            getTranslationList(property).forEach { langProperty ->
                requestFormMapping[langProperty]?.let { value ->
                    supportLanguage.takeIf { value is String && value.isNotBlank() }
                        ?.find { langProperty.startsWith(it) }?.also { translation[it] = value }
                }
                formMapping.remove(langProperty)
            }
            formMapping[property.lowercase()] = translation
        }
        return formMapping
    }

    private fun getTranslationList(property: String): ArrayList<String> {
        val resultList = arrayListOf<String>()
        supportLanguage.forEach { lang ->
            resultList.add("${lang}$property")
        }
        return resultList
    }
}