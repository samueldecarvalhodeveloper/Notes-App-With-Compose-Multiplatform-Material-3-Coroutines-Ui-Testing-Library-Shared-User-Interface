package org.example.notesapp.unitaries.user_interface.view_models

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders.ContentType
import io.ktor.http.HttpStatusCode.Companion.NotFound
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
import org.example.notesapp.mocks.UserViewModelMock
import org.example.notesapp.user_interface.view_models.UserViewModel
import orgexamplenotesappdatalocaldatabase.UserQueries
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserViewModelTest {
    private lateinit var userViewModel: UserViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var userGateway: UserGateway
    private lateinit var httpClientImplementation: HttpClient
    private lateinit var httpClientEngineMock: MockEngine
    private lateinit var userDataAccessObject: UserDataAccessObject
    private lateinit var userQueries: UserQueries

    @Test
    fun testIfVerifyIfUserExistsCommandTurnsIsUserAlreadyCreatedToTrueIfUserExistsAndSetsUserState() {
        runTest {
            var isUserCreated = false

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

            userViewModel = UserViewModelMock(userRepository)

            userViewModel.verifyIfUserExists {
                isUserCreated = true
            }

            assertTrue(isUserCreated)
        }
    }

    @Test
    fun testIfMethodCreateUserTurnsIsUserUsernameInvalidTrueIfUsernameIsEmpty() {
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

            userViewModel = UserViewModelMock(userRepository)

            userViewModel.createUser("") {}

            val isUserUsernameInvalid = userViewModel.isUserUsernameInvalidState.value

            assertTrue(isUserUsernameInvalid)
        }
    }

    @Test
    fun testIfMethodCreateUserTurnsIsInternetErrorRisenTrueIfServiceAccessIsNotAvailable() {
        runTest {
            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(""),
                    status = NotFound,
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

            userViewModel = UserViewModelMock(userRepository)

            userViewModel.createUser(USER_USERNAME) {}

            val isInternetErrorRisen = userViewModel.isInternetErrorRisenState.value

            assertTrue(isInternetErrorRisen)
        }
    }

    @Test
    fun testIfMethodCreateUserCreatesUserUserInterfaceState() {
        runTest {
            var isUserCreated = false

            httpClientEngineMock = MockEngine {
                respond(
                    content = ByteReadChannel(USER_JSON),
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

            userViewModel = UserViewModelMock(userRepository)

            userViewModel.createUser(USER_USERNAME) {
                isUserCreated = true
            }

            val createdUser = userViewModel.userState.value!!

            assertEquals(USER_ID, createdUser.id)
            assertEquals(USER_USERNAME, createdUser.username)

            assertTrue(isUserCreated)
        }
    }
}
