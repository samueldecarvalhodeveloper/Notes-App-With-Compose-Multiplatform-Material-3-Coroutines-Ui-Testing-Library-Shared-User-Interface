package org.example.notesapp.data.remote_data_source.gateways

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.notesapp.constants.data.DomainAgnosticConstants.SERVICE_URL
import org.example.notesapp.constants.data.UserConstants.USER_ROUTE
import org.example.notesapp.data.external_models.User
import org.example.notesapp.data.remote_data_source.models.UserModel

class UserGateway(private val httpClientImplementation: HttpClient) {
    suspend fun createUser(user: UserModel): User {
        return withContext(Dispatchers.IO) {
            return@withContext httpClientImplementation
                .post("${SERVICE_URL}${USER_ROUTE}/") {
                    contentType(Json)
                    setBody(user)
                }.body()
        }
    }
}