package org.example.notesapp.unitaries.data.remote_data_source.gateways

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.data.UserConstants.USER_JSON
import org.example.notesapp.constants.data.UserConstants.USER_MODEL_OBJECT
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.data.remote_data_source.gateways.UserGateway
import kotlin.test.Test
import kotlin.test.assertEquals

class UserGatewayTest {
    private lateinit var userGateway: UserGateway
    private lateinit var httpClientImplementation: HttpClient
    private lateinit var httpClientEngineMock: MockEngine

    @Test
    fun testIfMethodCreateUserCreatesUserOnService() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(USER_JSON),
                    status = Created,
                    headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
                )
            }
            httpClientImplementation = HttpClient(httpClientEngineMock) {
                install(ContentNegotiation) {
                    json()
                }
            }
            userGateway = UserGateway(httpClientImplementation)

            val createdUserOnService = userGateway.createUser(USER_MODEL_OBJECT)

            assertEquals(USER_ID, createdUserOnService.id)
            assertEquals(USER_USERNAME, createdUserOnService.username)
        }
    }
}