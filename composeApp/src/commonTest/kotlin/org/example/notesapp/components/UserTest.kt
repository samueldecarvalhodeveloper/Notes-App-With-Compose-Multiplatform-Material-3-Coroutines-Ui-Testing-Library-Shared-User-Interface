package org.example.notesapp.components

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.Created
import io.ktor.http.HttpStatusCode.Companion.OK
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.example.notesapp.concerns.DatabaseQueriesFactory
import org.example.notesapp.constants.data.DomainAgnosticConstants.JSON_CONTENT_TYPE_HEADER
import org.example.notesapp.constants.data.UserConstants.USER_ENTITY_OBJECT
import org.example.notesapp.constants.data.UserConstants.USER_ID
import org.example.notesapp.constants.data.UserConstants.USER_JSON
import org.example.notesapp.constants.data.UserConstants.USER_USERNAME
import org.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject
import org.example.notesapp.data.remote_data_source.gateways.UserGateway
import org.example.notesapp.data.repositories.UserRepository
import orgexamplenotesappdatalocaldatabase.UserQueries
import kotlin.test.Test
import kotlin.test.assertEquals

class UserTest {
    private lateinit var userRepository: UserRepository
    private lateinit var userGateway: UserGateway
    private lateinit var httpClientImplementation: HttpClient
    private lateinit var httpClientEngineMock: MockEngine
    private lateinit var userDataAccessObject: UserDataAccessObject
    private lateinit var userQueries: UserQueries

    @Test
    fun fetchingUserFromDatabase() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(""),
                    status = OK,
                    headers = headersOf(ContentType, JSON_CONTENT_TYPE_HEADER)
                )
            }
            httpClientImplementation = HttpClient(httpClientEngineMock) {
                install(ContentNegotiation) {
                    json()
                }
            }
            userGateway = UserGateway(httpClientImplementation)

            userQueries = DatabaseQueriesFactory.getUserQueriesInstance()

            userDataAccessObject = UserDataAccessObject(userQueries)

            userRepository = UserRepository(userGateway, userDataAccessObject)

            userDataAccessObject.createUser(USER_ENTITY_OBJECT)

            val userFromDatabase = userRepository.getUser()

            assertEquals(USER_ID, userFromDatabase.id)
            assertEquals(USER_USERNAME, userFromDatabase.username)
        }
    }

    @Test
    fun creatingUserOnServerAndOnDatabase() {
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

            userQueries = DatabaseQueriesFactory.getUserQueriesInstance()

            userDataAccessObject = UserDataAccessObject(userQueries)

            userRepository = UserRepository(userGateway, userDataAccessObject)

            val createdUserOnService = userRepository.getCreatedUser(USER_USERNAME)

            assertEquals(USER_ID, createdUserOnService.id)
            assertEquals(USER_USERNAME, createdUserOnService.username)
        }
    }
}