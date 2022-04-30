package com.jarvis.acg.api.route.base

import com.jarvis.acg.api.kmongo.model.base.BaseObject
import com.jarvis.acg.api.model.SubPathEndpoint
import com.jarvis.acg.api.model.file.BaseFile
import com.jarvis.acg.api.util.ExtensionUtil.getResponse
import com.jarvis.acg.api.util.ModelUtil
import com.jarvis.acg.api.util.TranslationUtil
import com.mongodb.client.MongoCollection
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlinx.coroutines.coroutineScope
import org.bson.conversions.Bson
import org.litote.kmongo.`in`
import org.litote.kmongo.findOne
import org.litote.kmongo.findOneById

abstract class BaseEntryRoute<E : BaseObject<E>> : BaseRoute(), BaseEntryRouteInterface<E> {

    protected abstract var modelEntry: MongoCollection<E>
    protected abstract var entryPathSection: String

    protected open var translationList: List<String>? = null
    protected open var trimList: List<String>? = null
    protected open var fileNameList: List<String>? = null
    protected open var subPathEndpointList: List<SubPathEndpoint>? = null

    protected open fun createHandling(requestFormMapping: HashMap<String, Any?>) : HashMap<String, Any?>? = null
    protected open fun createdHandling(obj: E, requestFormMapping: HashMap<String, Any?>) : E? = null
    protected open suspend fun createdHandlingFile(obj: E, baseFileList: ArrayList<BaseFile>?) : E? = null

    protected open var isEnableDefaultGetById = true
    protected open var isEnableDefaultCreate = true
    protected open var isEnableDefaultUpdate = true
    protected open var isEnableDefaultDeleteById = true

    override fun createRoute(routing: Routing) = routing {
        route("/$entryPathSection") {
            if (isEnableDefaultGetById) get("{id}") { getById(call) }
            if (isEnableDefaultCreate) post("") { create(call) }
            if (isEnableDefaultUpdate) put("{id}") { updateById(call) }
            if (isEnableDefaultDeleteById) delete("{idListStr}") { deleteByIdList(call) }

            get("list") { getResponseList(call) }

            get("list/{idList}") {
                getListByIdList(call)
            }

            subPathEndpointList?.takeUnless { it.isEmpty() }?.let { list ->
                list.forEach { obj ->
                    handleExtraEndpoint(obj, this)
                }
            }

            initExtraRoute(this)
        }
    }

    suspend fun getListByIdList(call: ApplicationCall) = coroutineScope {
        call.parameters["idList"]?.let { id ->
            val idList = id.split("_")
            getResponseListByStatement(BaseObject<*>::_id `in` idList, call)
        }
    }

    suspend fun getById(call: ApplicationCall) = coroutineScope {
        call.parameters["id"]?.let { id ->
            getResponseById(id, call)
        }
    }

    suspend fun create(call: ApplicationCall) = coroutineScope {
        // receive form param

        var requestFormMapping = ModelUtil().convertFormMapping(call.receiveMultipart(), trimList)
        translationList?.takeIf { it.isNotEmpty() && !translationList.isNullOrEmpty() }?.let {
            requestFormMapping = TranslationUtil().convertPropertyToTranslation(requestFormMapping, translationList)
        }
        val baseFileList = fileNameList?.takeIf { it.isNotEmpty() }?.let { ModelUtil().requestFileHandling(requestFormMapping, it) }
        val entry = createNewGenericObject()
        createHandling(requestFormMapping)
        entry.convertMappingToProperty(entry, requestFormMapping)
        createdHandling(entry, requestFormMapping)
        createdHandlingFile(entry, baseFileList)

        // handle save file if needed

        modelEntry.insertOne(entry)

        getResponseById(entry._id, call, HttpStatusCode.Created)
    }

    suspend fun updateById(call: ApplicationCall) = coroutineScope {

    }

    suspend fun deleteByIdList(call: ApplicationCall) = coroutineScope {
        call.parameters["idListStr"]?.let { idListStr ->
            val idList = idListStr.split('_')

            performDeleteAndGetResponse(idList, call)
        }
    }

    suspend fun getEntryObjectResponseById(id: String?) : E? {
        id?.let { return getEntryById(id) }
        return null
    }

    suspend fun getResponseById(id: String?, call: ApplicationCall, statusCode: HttpStatusCode = HttpStatusCode.OK) {
        getEntryObjectResponseById(id)?.let {
            call.respondText(it.getResponse(), ContentType.Text.Plain, statusCode)
        } ?: run {
            // todo
            call.respondText("Error", ContentType.Text.Plain, HttpStatusCode.InternalServerError)
        }
    }

    fun getEntryById(id: String) : E? {
        modelEntry.findOneById(id)?.let { obj ->
            return obj
        }
        return null
    }

    suspend fun getEntryList() : List<E> {
        modelEntry.find().toList().let { list ->
            return list
        }
    }

    suspend fun getEntryListByIdList(idList: List<String>?) : List<E>? {
        idList?.takeIf { it.isNotEmpty() }?.let {
            modelEntry.find(BaseObject<*>::_id `in` idList).toList().let { list ->
                return list
            }
        }
        return null
    }

    private suspend fun getResponseList(call: ApplicationCall, statusCode: HttpStatusCode = HttpStatusCode.OK) {
        getEntryList().let {
            call.respondText(it.getResponse(), ContentType.Text.Plain, statusCode)
        }
    }

    suspend fun getResponseListByIdList(idList: List<String>, call: ApplicationCall, statusCode: HttpStatusCode = HttpStatusCode.OK) {
        getEntryListByIdList(idList)?.let {
            call.respondText(it.getResponse(), ContentType.Text.Plain, statusCode)
        }
    }

    suspend fun getEntryByStatement(statement: Bson): E? {
        return modelEntry.findOne(statement)
    }

    suspend fun getEntryByStatement(statement: String): E? {
        return modelEntry.findOne(statement)
    }

    suspend fun getEntryByStatement(statement: Array<Bson>): E? {
        return modelEntry.findOne(*statement)
    }

    suspend fun getResponseListByStatement(statement: Bson, call: ApplicationCall, statusCode: HttpStatusCode = HttpStatusCode.OK) {
        getEntryObjectListByStatement(statement).let {
            call.respondText(it.getResponse(), ContentType.Text.Plain, statusCode)
        }
    }

    suspend fun getEntryObjectListByStatement(statement: Bson) : List<E> {
        return modelEntry.find(statement).toList()
    }

    suspend fun performDeleteAndGetResponse(idList: List<String>, call: ApplicationCall) {
        modelEntry.deleteMany(BaseObject<E>::_id `in` idList).let { result ->
            if (idList.size == result.deletedCount.toInt()) {
                // todo
                call.respondText("success", ContentType.Text.Plain, HttpStatusCode.OK)
            } else {
                // todo
                call.respondText("Error", ContentType.Text.Plain, HttpStatusCode.InternalServerError)
            }
        }
    }

    private fun handleExtraEndpoint(subPathEndpoint: SubPathEndpoint, route: Route) = route {
        when (subPathEndpoint.requestMethod) {
            HttpMethod.Get.value -> {
                get(subPathEndpoint.subPath) { getEntryObjectListByStatement(subPathEndpoint.statement) }
            }
        }
    }
}